package application.services;

import application.model.Illness;
import application.model.PatientIllness;
import application.model.dto.IllnessDTO;
import application.repositories.IllnessRepository;
import application.repositories.PatientIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Illness saveIllness(Illness illness) {
        return illnessRepository.save(illness);
    }

    public Illness findIllnessById(Long id) {
       return illnessRepository.findById(id).orElse(null);
    }

    public PatientIllness assignIllnessToPatient(PatientIllness patientIllness) {
        return patientIllnessRepository.save(patientIllness);
    }

    public List<Illness> findIllnessesNotPresentInPatient(Long patientId) {
        return illnessRepository.findIllnessesNotPresentInPatient(patientId);
    }

    @Transactional
    public void deleteIllnessFromPatient(Long patientId, Long illnessId) {
        patientIllnessRepository.deleteIllnessFromPatient(patientId, illnessId);
    }
}
