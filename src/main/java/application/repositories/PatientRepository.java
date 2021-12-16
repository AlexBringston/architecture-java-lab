package application.repositories;

import application.model.Patient;
import application.model.dto.PatientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDepartmentId(Long departmentId);

    @Query("SELECT h FROM humans h WHERE UPPER(CONCAT(h.name, ' ', h.surname)) LIKE UPPER(CONCAT('%',:query,'%'))\n" +
            "OR UPPER(CONCAT(h.surname,' ', h.name)) LIKE UPPER(CONCAT('%',:query,'%'))")
    List<Patient> findByQuery(@Param("query")String query);


    @Query("SELECT new application.model.dto.PatientDTO(h.name, h.surname, h.numOfIllnesses, h" +
            ".department.id, hs.startedAt, i.name)" +
            " FROM humans h JOIN humans_illnesses hs ON h.id = hs.patient.id " +
            "JOIN illnesses i on hs.illness.id = i.id ORDER BY hs.startedAt DESC")
    List<PatientDTO> findAllPatientsSortedByIllnessTime();
}
