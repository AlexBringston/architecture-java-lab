package application.services;

import application.model.Patient;
import application.model.dto.PatientDTO;
import application.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(new Patient());
    }

    public List<Patient> findByQuery(String query) {
        return patientRepository.findByQuery(query);
    }

    public List<Patient> findPatientsInOrderOfNumOfIllnessesDesc() {
        return patientRepository.findAll(Sort.by(Sort.Direction.DESC,"numOfIllnesses"));
    }

    public List<PatientDTO> findAllOrderByIllnessLastingTime() {
        return patientRepository.findAllPatientsSortedByIllnessTime();
    }
}
