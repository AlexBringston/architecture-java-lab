package application.repositories;

import application.model.PatientIllness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientIllnessRepository extends JpaRepository<PatientIllness, Long> {

    long countByIllnessId(Long illnessId);
}
