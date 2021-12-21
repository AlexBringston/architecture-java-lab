package application.repositories;

import application.model.Department;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM departments d WHERE (d.numOfBeds - d.numOfBusyBeds) >= ?1 ORDER BY (d.numOfBeds - d" +
            ".numOfBusyBeds) DESC")
    List<Department> findFreeDepartments(Integer number);
}
