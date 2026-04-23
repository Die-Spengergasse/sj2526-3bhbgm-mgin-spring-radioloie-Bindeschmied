package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.Machine;
import at.spengergasse.spring_thymeleaf.entities.MachineRepository;
import at.spengergasse.spring_thymeleaf.entities.reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/machine")
public class MachineController {
    private final MachineRepository machineRepository;

    public MachineController(MachineRepository machineRepository) {this.machineRepository = machineRepository;}
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("machine", new Machine());
        return "add_machine";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("machine") Machine machine) {
        machineRepository.save(machine);
        return "redirect:/machine/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("machines", machineRepository.findAll());
        return "machinelist";
    }

}
