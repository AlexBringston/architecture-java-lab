package application.repositories;

import application.model.Category;
import application.model.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Long> {

    Long countIllnessByCategory(Category category);

    @Query("SELECT i FROM illnesses i WHERE i.id NOT IN (SELECT pi.illness FROM " +
            "patients_illnesses pi WHERE pi.patient.id = :patientId)")
    List<Illness> findIllnessesNotPresentInPatient(@Param("patientId")Long patientId);

}
