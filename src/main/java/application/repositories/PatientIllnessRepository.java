package application.repositories;

import application.model.PatientIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientIllnessRepository extends JpaRepository<PatientIllness, Long> {

    long countByIllnessId(Long illnessId);

    @Modifying
    @Query("DELETE FROM application.model.PatientIllness pi WHERE pi.patient.id = ?1 AND pi.illness.id = ?2")
    void deleteIllnessFromPatient(Long patientId, Long illnessId);
}
