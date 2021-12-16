package application.controller;

import application.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class RegionController {

    private RegionRepository regionRepository;

    @Autowired
    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/regions")
    public String showRegions(Model model) {
        model.addAttribute("contents", regionRepository.findAll());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID","Name")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","name")));
        return "table";
    }
}
