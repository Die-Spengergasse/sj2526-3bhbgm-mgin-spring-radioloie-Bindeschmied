package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.*;
import at.spengergasse.spring_thymeleaf.entities.MachineRepository;
import at.spengergasse.spring_thymeleaf.entities.reservation;
import at.spengergasse.spring_thymeleaf.entities.MachineRepository;
import at.spengergasse.spring_thymeleaf.entities.PatientRepository;
import at.spengergasse.spring_thymeleaf.entities.reservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class reservationController {

    private final reservationRepository reservationRep;
    private final MachineRepository machineRep;
    private final PatientRepository patientRep;

    public reservationController(reservationRepository reservationRepository,
                                 MachineRepository machineRepository,
                                 PatientRepository patientRepository) {
        this.reservationRep = reservationRepository;
        this.machineRep = machineRepository;
        this.patientRep = patientRepository;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("reservation", new reservation());
        model.addAttribute("machines", machineRep.findAll());
        model.addAttribute("patients", patientRep.findAll());
        return "add_reservation";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("reservation") reservation reservation,
                      @RequestParam int patientId,
                      @RequestParam int machineId,
                      BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(result.getAllErrors().get(0).getDefaultMessage());
        }
        List<reservation> resList = reservationRep.findByPatientId(patientId);
        for (reservation r : resList)
        {
            if (r.getDatetime().equals(reservation.getDatetime()))
            {
                throw new IllegalArgumentException("Patient cant have several reservations at the same time");
            }
        }
        Patient patient = patientRep.findById(patientId);

        resList = reservationRep.findByMachineId(machineId);
        for (reservation r : resList)
        {
            if (r.getDatetime().equals(reservation.getDatetime()))
            {
                throw new IllegalArgumentException("Machine cant have several reservations at the same time");
            }
        }
        Machine machine = machineRep.findById(machineId);



        reservation.setPatient(patient);
        reservation.setMachine(machine);

        reservationRep.save(reservation);

        return "redirect:/reservation/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("reservations", reservationRep.findAll());
        model.addAttribute("machines", machineRep.findAll());
        return "reservationlist";
    }

    @GetMapping("/listmachine")
    public String listmachine(@RequestParam int machineId, Model model) {

        List<reservation> reservations = reservationRep.findByMachineId(machineId);

        model.addAttribute("reservations", reservations);

        return "machinereservationlist";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        if (ex instanceof TransactionException) {
            model.addAttribute("message", "Connection error. Database refused connection");
        }
        else{
            model.addAttribute("message", ex.getMessage());
        }
        return "error";
    }
}