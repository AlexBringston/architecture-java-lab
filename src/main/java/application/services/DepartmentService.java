package application.services;

import application.model.Department;
import application.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public List<Department> findByOrderByNumOfBedsDesc() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "numOfBeds"));
    }

    public Department findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Department saveDepartment(Department department) {
        return repository.save(department);
    }

    public List<Department> findFreeDepartments(Integer number) {
        return repository.findFreeDepartments(number);
    }

}
