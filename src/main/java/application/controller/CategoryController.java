package application.controller;

import application.model.Category;
import application.services.CategoryService;
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
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "forms/adding-new-category";
    }
    @PostMapping("/category")
    public String addCategory(@Valid @ModelAttribute("category")Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/adding-new-category";
        }

        category = categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("contents", categoryService.findAll());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Category ID", "Category name")));
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("id", "name")));
        return "table";
    }
}
