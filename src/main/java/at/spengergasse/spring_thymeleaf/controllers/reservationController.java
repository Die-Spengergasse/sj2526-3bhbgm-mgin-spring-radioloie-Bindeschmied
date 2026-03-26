package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.*;
import at.spengergasse.spring_thymeleaf.entities.MachineRepository;
import at.spengergasse.spring_thymeleaf.entities.reservation;
import at.spengergasse.spring_thymeleaf.entities.MachineRepository;
import at.spengergasse.spring_thymeleaf.entities.PatientRepository;
import at.spengergasse.spring_thymeleaf.entities.reservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                      @RequestParam int machineId) {

        Patient patient = patientRep.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        Machine machine = machineRep.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid machine Id:" + machineId));

        reservation.setPatient(patient);
        reservation.setMachine(machine);

        reservationRep.save(reservation); // einmalig speichern, wenn alles gesetzt ist

        return "redirect:/reservation/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("reservations", reservationRep.findAll());
        return "reservationlist";
    }
}