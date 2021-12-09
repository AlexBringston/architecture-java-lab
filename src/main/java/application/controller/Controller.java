package application.controller;

import application.PatientRepository;
import application.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class Controller {

    private final PatientRepository repository;

    @Autowired
    public Controller(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/patients")
    public List<Patient> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/patients/{id}")
    public Patient findById(@PathVariable("id") Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/patients")
    public Patient addPatient(@RequestBody Patient newPatient) {
        return repository.save(newPatient);
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@RequestBody Patient newPatient, @PathVariable("id") Long id) {

        return repository.findById(id)
                .map(patient -> {
                    patient.setName(newPatient.getName());
                    patient.setSurname(newPatient.getSurname());
                    patient.setNumOfIllnesses(newPatient.getNumOfIllnesses());
                    patient.setDepartmentId(newPatient.getDepartmentId());
                    return repository.save(patient);
                }).orElseGet(() -> {
                    newPatient.setId(id);
                    return repository.save(newPatient);
                });
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
