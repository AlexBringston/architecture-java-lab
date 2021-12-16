package application.controller;

import application.services.CategoryService;
import application.services.IllnessService;
import application.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class IllnessController {

    private IllnessService illnessService;
    private CategoryService categoryService;
    private RegionService regionService;

    @Autowired
    public IllnessController(IllnessService illnessService, CategoryService categoryService, RegionService regionService) {
        this.illnessService = illnessService;
        this.categoryService = categoryService;
        this.regionService = regionService;
    }

    @GetMapping("/illnesses")
    public String defaultValue(Model model) {
        model.addAttribute("contents", illnessService.findAll());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID","Name", "Category")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","name", "category.name")));
        return "table";
    }

    @GetMapping("/illnesses/count/byCategory")
    public String countByCategory(Model model) {
        model.addAttribute("contents", categoryService.createEntries());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Category ID", "Category name","Num of ill")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","categoryName","numOfIll")));
        return "table";
    }

    @GetMapping("/illnesses/count/byRegion")
    public String countByDepartment(Model model) {
        model.addAttribute("contents", regionService.createEntries());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Region ID", "Region name","Num of ill")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","regionName","numOfIll")));
        return "table";
    }

    @GetMapping("/illnesses/count")
    public String countIllnessesNumber(Model model) {
        model.addAttribute("contents", illnessService.createEntries());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Illness id", "Illness name",
                "Illness category","Cases registered")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","name","categoryName", "numOfIll")));
        return "table";
    }
}
