package application.controller;

import application.model.Department;
import application.model.Region;
import application.services.DepartmentService;
import application.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final RegionService regionService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, RegionService regionService) {
        this.departmentService = departmentService;
        this.regionService = regionService;
    }

    @GetMapping("/departments")
    public String defaultValue(Model model) {
        model.addAttribute("contents", departmentService.findAll());
        return setAttributes(model);
    }

    @GetMapping("/departments/ordered")
    public String orderedDepartments(Model model) {
        model.addAttribute("contents", departmentService.findByOrderByNumOfBedsDesc());
        return setAttributes(model);
    }

    @GetMapping("/departments/free")
    public String orderedDepartments(@RequestParam(name = "num", required = false, defaultValue = "1")Integer num,
                                     Model model) {
        model.addAttribute("contents", departmentService.findFreeDepartments(num));
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID", "Total beds", "Occupied beds",
                "Region")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "numOfBeds", "numOfBusyBeds", "region.name")));
        model.addAttribute("action", "departments");
        return "freeDepartments";
    }

    @GetMapping("/department")
    public String showPatientForm(Model model) {
        Department department = new Department();
        department.setRegion(new Region());
        model.addAttribute("department", department);
        return "forms/adding-new-department";
    }

    @PostMapping("/department")
    public String addDepartment(@Valid @ModelAttribute("department") Department department, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/adding-new-department";
        }

        Region region = regionService.findRegionByName(department.getRegion().getName());
        if (region != null) {
            department.setRegion(region);
        }
        else {
            department.setRegion(regionService.saveRegion(department.getRegion()));
        }
        department = departmentService.saveDepartment(department);
        System.out.println(department);
        return "redirect:/departmentPatients";

    }

    private String setAttributes(Model model) {
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID", "Total beds", "Occupied beds",
                "Region")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "numOfBeds", "numOfBusyBeds", "region.name")));
        model.addAttribute("action", "departments");
        return "departmentPatients";
    }

}
