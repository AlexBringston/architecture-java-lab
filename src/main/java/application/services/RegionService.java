package application.services;

import application.model.Department;
import application.model.Patient;
import application.model.Region;
import application.model.dto.RegionDTO;
import application.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public RegionDTO mapToRegionDTO(Region region) {
        return new RegionDTO(region.getId(), region.getName(), getNumberOfIllForARegion(region));
    }
    public Long getNumberOfIllForARegion(Region region) {
        return region.getDepartments()
                .stream()
                .map(Department::getPatients)
                .map(patients -> patients
                        .stream()
                        .map(Patient::getNumOfIllnesses)
                        .count()
                )
                .findFirst()
                .orElse(0L);
    }

    public List<RegionDTO> createEntries() {
        return regionRepository.findAll()
                .stream()
                .map(this::mapToRegionDTO)
                .collect(Collectors.toList());
    }

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    public Region findRegionByName(String name) {
        return regionRepository.findRegionByName(name).orElse(null);
    }
}
