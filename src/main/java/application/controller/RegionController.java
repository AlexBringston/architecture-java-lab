package application.controller;

import application.model.Region;
import application.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class RegionController {

    private RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions")
    public String showRegions(Model model) {
        model.addAttribute("contents", regionService.findAll());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID","Name")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","name")));
        return "table";
    }

    @GetMapping("/region")
    public String showRegionForm(Model model) {
        model.addAttribute("region", new Region());
        return "forms/adding-new-region";
    }
    @PostMapping("/region")
    public String addRegion(@Valid @ModelAttribute("region")Region region, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/adding-new-region";
        }

        region = regionService.saveRegion(region);
        return "redirect:/regions";

    }
}
