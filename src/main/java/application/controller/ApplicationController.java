package application.controller;

import application.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @Autowired
    private PatientRepository repository;

    @GetMapping("/")
    public String defaultValue(Model model) {
        model.addAttribute("patients", repository.findAll());
        return "patients";
    }
}
