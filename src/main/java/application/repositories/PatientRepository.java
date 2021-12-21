package application.repositories;

import application.model.Patient;
import application.model.dto.PatientDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDepartmentId(Long departmentId, Sort sort);

    Optional<Patient> findById(Long departmentId, Sort sort);

    @Query("SELECT h FROM patients h WHERE UPPER(CONCAT(h.name, ' ', h.surname)) LIKE UPPER(CONCAT('%',:query,'%'))\n" +
            "OR UPPER(CONCAT(h.surname,' ', h.name)) LIKE UPPER(CONCAT('%',:query,'%'))")
    List<Patient> findByQuery(@Param("query")String query, Sort sort);


    @Query("SELECT new application.model.dto.PatientDTO(p.name, p.surname, p.numOfIllnesses," +
            " p.department.id, ps.startedAt, i.name, p.age, p.phoneNumber)" +
            " FROM patients p JOIN patients_illnesses ps ON p.id = ps.patient.id " +
            "JOIN illnesses i on ps.illness.id = i.id ORDER BY ps.startedAt DESC")
    List<PatientDTO> findAllPatientsSortedByIllnessTime();

    @Modifying
    @Query("UPDATE application.model.Patient p SET p.numOfIllnesses = ?1 WHERE p.id = ?2")
    void updateNumOfIllnesses(Long numOfIllnesses, Long patientId);

}
