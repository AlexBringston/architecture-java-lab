package application.services;

import application.model.Illness;
import application.model.dto.IllnessDTO;
import application.repositories.IllnessRepository;
import application.repositories.PatientIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IllnessService {

    private final IllnessRepository illnessRepository;
    private final PatientIllnessRepository patientIllnessRepository;

    @Autowired
    public IllnessService(IllnessRepository illnessRepository, PatientIllnessRepository patientIllnessRepository) {
        this.illnessRepository = illnessRepository;
        this.patientIllnessRepository = patientIllnessRepository;
    }

    public List<Illness> findAll() {
        return illnessRepository.findAll();
    }
    private IllnessDTO mapToIllnessDTO(Illness illness) {
        return new IllnessDTO(illness.getId(), illness.getName(), illness.getCategory().getName(),
                patientIllnessRepository.countByIllnessId(illness.getId()));
    }

    public List<IllnessDTO> createEntries() {
        return illnessRepository.findAll()
                .stream()
                .map(this::mapToIllnessDTO)
                .collect(Collectors.toList());
    }
}
