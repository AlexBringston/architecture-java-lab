package application.controller;

import application.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class DepartmentController {

    private DepartmentRepository repository;

    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/departments")
    public String defaultValue(Model model) {
        model.addAttribute("contents", repository.findAll());
        return setAttributes(model);
    }

    @GetMapping("/departments/ordered")
    public String orderedDepartments(Model model) {
        model.addAttribute("contents", repository.findByOrderByNumOfBedsDesc());
        return setAttributes(model);
    }

    private String setAttributes(Model model) {
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID","Total beds", "Free beds",
                "Region", "Option")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","numOfBeds","numOfBusyBeds","region.name")));
        return "table";
    }

}
