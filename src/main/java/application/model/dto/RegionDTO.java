package application.model.dto;

import lombok.Data;

@Data
public class RegionDTO {

    private Long id;
    private String regionName;
    private Long numOfIll;

    public RegionDTO(Long id, String regionName, Long numOfIll) {
        this.id = id;
        this.regionName = regionName;
        this.numOfIll = numOfIll;
    }
}
