package application.controller;

import application.model.Category;
import application.model.Illness;
import application.services.CategoryService;
import application.services.IllnessService;
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
public class IllnessController {

    private final IllnessService illnessService;
    private final CategoryService categoryService;
    private final RegionService regionService;

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

    @GetMapping("/illnesses/count")
    public String countByOption(@RequestParam(name = "option", defaultValue = "",required = false) String option,
            Model model) {
        switch (option) {
            case "":
            case "total":
                model.addAttribute("contents", illnessService.createEntries());
                model.addAttribute("headers", new ArrayList<>(Arrays.asList("Illness id", "Illness name",
                        "Illness category", "Cases registered")));
                model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "name", "categoryName", "numOfIll")));
                break;
            case "category":
                model.addAttribute("contents", categoryService.createEntries());
                model.addAttribute("headers", new ArrayList<>(Arrays.asList("Category ID", "Category name", "Num of ill")));
                model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "categoryName", "numOfIll")));
                break;
            case "region":
                model.addAttribute("contents", regionService.createEntries());
                model.addAttribute("headers", new ArrayList<>(Arrays.asList("Region ID", "Region name", "Num of ill")));
                model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "regionName", "numOfIll")));
                break;
            default:

                break;
        }
        model.addAttribute("option", option);
        return "illnesses";
    }

    @GetMapping("/illness")
    public String showIllnessForm(Model model) {
        Illness illness = new Illness();
        illness.setCategory(new Category());
        model.addAttribute("illness", illness);
        System.out.println(illness);
        return "forms/adding-new-illness";
    }


    @PostMapping("/illness")
    public String addIllness(@Valid @ModelAttribute("illness")Illness illness, BindingResult bindingResult) {
        System.out.println(illness);
        if (bindingResult.hasErrors()) {
            return "forms/adding-new-illness";
        }

        Category category = categoryService.findCategoryByName(illness.getCategory().getName());
        System.out.println(category);
        if (category != null) {
            illness.setCategory(category);
        }
        else {
            illness.setCategory(categoryService.saveCategory(illness.getCategory()));
        }

        illness = illnessService.saveIllness(illness);
        System.out.println(illness);

        return "redirect:/illnesses";
    }
}
