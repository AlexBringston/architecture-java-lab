package application.controller;

import application.model.*;
import application.services.DepartmentService;
import application.services.IllnessService;
import application.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.comparator.Comparators;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final DepartmentService departmentService;
    private final IllnessService illnessService;

    @Autowired
    public PatientController(PatientService patientService, DepartmentService departmentService, IllnessService illnessService) {
        this.patientService = patientService;
        this.departmentService = departmentService;
        this.illnessService = illnessService;
    }

    @GetMapping("/patients")
    public String getPatientsByQuery(@RequestParam(name = "query", defaultValue = "", required = false) String query,
                                     @RequestParam(name = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("contents", patientService.findById(id));
        } else {
            model.addAttribute("contents", patientService.findByQuery(query));
        }
        return setParameters(model);
    }

    @GetMapping("/patients/ordered")
    public String getPatientsOrderByNumberOfIllnesses(Model model) {
        model.addAttribute("contents", patientService.findPatientsInOrderOfNumOfIllnessesDesc());
        return setParameters(model);
    }

    private String setParameters(Model model) {
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID", "Name", "Surname", "Age",
                "Total ills amount", "Department ID")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "name", "surname", "age",
                "numOfIllnesses", "department.id")));
        model.addAttribute("action", "patients");
        return "patients";
    }

    @GetMapping("/patients/withIllnesses")
    public String getPatientsOrderByIllnessLength(Model model) {
        model.addAttribute("contents", patientService.findAllOrderByIllnessLastingTime());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Name", "Surname", "Total ills amount",
                "Department ID", "Started at", "Illness")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("name", "surname", "numOfIllnesses",
                "departmentId", "startedAt", "illnessName")));
        model.addAttribute("action", "patients");
        return "table";
    }

    @GetMapping("/patient")
    public String showPatientForm(Model model) {
        Patient patient = new Patient();
        patient.setDepartment(new Department());
        model.addAttribute("patient", patient);
        return "forms/adding-new-patient";
    }

    @PostMapping("/patient")
    public String addPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "forms/adding-new-patient";
        }
        if (!patient.getPhoneNumber().matches("[0-9]+")) {
            bindingResult.rejectValue("phoneNumber", "error.phone", "Phone number must contain only numbers");
            return "forms/adding-new-patient";
        }

        Department department = departmentService.findById(patient.getDepartment().getId());
        if (department != null) {
            patient.setDepartment(department);
        }
        patient.setNumOfIllnesses(0L);
        patient = patientService.savePatient(patient);
        System.out.println(patient);
        return "redirect:/patients";

    }

    @GetMapping("/departments/{id}/patients")
    public String showPatientsOfDepartment(Model model, @PathVariable Long id) {
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute("contents", patientService.findByDepartmentId(id));
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID", "Name", "Surname", "Age",
                "Total ills amount")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "name", "surname", "age",
                "numOfIllnesses")));
        model.addAttribute("action", "patients");
        return "departmentPatients";
    }

    @GetMapping("/patients/{id}/illnesses")
    public String getPatientIllnesses(Model model, @PathVariable Long id) {
        model.addAttribute("patient", patientService.findById(id));
        model.addAttribute("contents",
                patientService.findById(id)
                        .getPatientIllnesses()
                        .stream()
                        .map(PatientIllness::getIllness)
                        .sorted(Comparator.comparing(Illness::getId))
                        .collect(Collectors.toList()));
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Illness ID", "Illness name", "Category",
                "Option")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "name", "category.name", "delete")));
        return "operateIllness";
    }

    @GetMapping("/choosePatient/{id}")
    public String findPatient(Model model, @PathVariable Long id, HttpSession httpSession) {
        model.addAttribute("option", "true");
        model.addAttribute("patient", patientService.findById(id));
        PatientIllness patientIllness = new PatientIllness();
        patientIllness.setPatient(patientService.findById(id));

        patientIllness.setIllness(new Illness());
        patientIllness.setIllnessStatus(0L);
        httpSession.setAttribute("patientIllness", patientIllness);
        model.addAttribute("patientIllness", patientIllness);
        model.addAttribute("contents", illnessService.findIllnessesNotPresentInPatient(id));

        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID", "Name", "Category",
                "Severity status", "Option")));
        System.out.println(patientIllness);
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id", "name", "category.name", "status", "href")));
        return "assignIllnessToPatient";
    }

    @PostMapping("/choosePatient")
    public String addPatientIllness(@Valid @ModelAttribute("patientIllness") PatientIllness patientIllness,
                                    BindingResult bindingResult, Model model, HttpSession httpSession) {
        model.addAttribute("patient", patientIllness.getPatient());
        System.out.println(patientIllness);
        if (bindingResult.hasErrors()) {
            return "assignIllnessToPatient";
        }

        patientService.addIllnessToPatient(patientIllness);
        httpSession.removeAttribute("patientIllness");
        return "redirect:/patients/" + patientIllness.getPatient().getId() + "/illnesses";
    }

    @ModelAttribute("patientIllness")
    public PatientIllness getPatientIllnessFromSession(HttpSession session) {
        if (session.getAttribute("patientIllness") != null) {
            return (PatientIllness) session.getAttribute("patientIllness");
        } else {
            return new PatientIllness();
        }
    }

    @PostMapping("/deleteIllnessFromPatient")
    public String deleteIllnessFromPatient(@RequestParam(name = "illnessId", required = false) Long illnessId,
                                           @RequestParam(name = "patientId", required = false) Long patientId,
                                           Model model) {
        System.out.println("PATIENT" + patientId);
        System.out.println("ILLNESS" + illnessId);
        Patient patient = patientService.findById(patientId);
        if (patient.getNumOfIllnesses() == 0) {
            return "redirect:/patients/" + patientId + "/illnesses";
        }
        illnessService.deleteIllnessFromPatient(patientId, illnessId);
        patientService.updateNumOfIllnesses(patient.getNumOfIllnesses() - 1, patientId);
        return "redirect:/patients/" + patientId + "/illnesses";
    }
}
