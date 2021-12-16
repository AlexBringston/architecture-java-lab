package application.model.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Long numOfIll;

    public CategoryDTO(Long id, String categoryName, Long numOfIll) {
        this.id = id;
        this.categoryName = categoryName;
        this.numOfIll = numOfIll;
    }
}
