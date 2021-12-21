package application.services;

import application.model.Illness;
import application.model.Patient;
import application.model.PatientIllness;
import application.model.PatientIllnessKey;
import application.model.dto.PatientDTO;
import application.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private IllnessService illnessService;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setIllnessService(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(new Patient());
    }

    public List<Patient> findByQuery(String query) {
        return patientRepository.findByQuery(query, Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Patient> findPatientsInOrderOfNumOfIllnessesDesc() {
        return patientRepository.findAll(Sort.by(Sort.Direction.DESC,"numOfIllnesses"));
    }

    public List<PatientDTO> findAllOrderByIllnessLastingTime() {
        return patientRepository.findAllPatientsSortedByIllnessTime();
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public void updateNumOfIllnesses(Long numOfIllnesses, Long patientId) {
        patientRepository.updateNumOfIllnesses(numOfIllnesses,patientId);
    }

    public List<Patient> findByDepartmentId(Long departmentId) {
        return patientRepository.findByDepartmentId(departmentId, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    public void addIllnessToPatient(PatientIllness patientIllness) {
        Illness illness = illnessService.findIllnessById(patientIllness.getIllness().getId());
        patientIllness.setIllness(illness);
        patientRepository.updateNumOfIllnesses(patientIllness.getPatient().getNumOfIllnesses() + 1,
                patientIllness.getPatient().getId());
        patientIllness.setKey(new PatientIllnessKey(patientIllness.getPatient().getId(), patientIllness.getIllness().getId()));
        patientIllness.setStartedAt(LocalDate.now());
        patientIllness = illnessService.assignIllnessToPatient(patientIllness);
    }
}
