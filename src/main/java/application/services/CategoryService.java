package application.services;

import application.model.Category;
import application.model.dto.CategoryDTO;
import application.repositories.CategoryRepository;
import application.repositories.IllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final IllnessRepository illnessRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, IllnessRepository illnessRepository) {
        this.categoryRepository = categoryRepository;
        this.illnessRepository = illnessRepository;
    }

    private CategoryDTO mapToCategoryDTO(Category category, Long numOfIll) {
        return new CategoryDTO(category.getId(), category.getName(), numOfIll);
    }

    public List<CategoryDTO> createEntries() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> mapToCategoryDTO(category,
                illnessRepository.countIllnessByCategory(category))).collect(Collectors.toList());
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name).orElse(null);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}
