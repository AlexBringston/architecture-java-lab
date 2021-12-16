package application.model.dto;

import lombok.Data;

@Data
public class IllnessDTO {

    private Long id;
    private String name;
    private String categoryName;
    private Long numOfIll;

    public IllnessDTO(Long id, String name, String categoryName, Long numOfIll) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.numOfIll = numOfIll;
    }
}
