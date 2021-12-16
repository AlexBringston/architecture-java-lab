package application.controller;

import application.repositories.PatientRepository;
import application.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String getPatientsByQuery(@RequestParam(name = "query", defaultValue = "",required = false) String query,
                                     @RequestParam(name = "id",required = false)Long id, Model model) {
        if(id != null) {
            model.addAttribute("contents", patientService.findById(id));
        }
        else {
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
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("ID","Name", "Surname","Total ills amount",
                "Department ID")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("id","name","surname","numOfIllnesses",
                "department.id")));
        model.addAttribute("action","patients");
        return "table";
    }

    @GetMapping("/patients/illnesses")
    public String getPatientsOrderByIllnessLength(Model model) {
        model.addAttribute("contents", patientService.findAllOrderByIllnessLastingTime());
        model.addAttribute("headers", new ArrayList<>(Arrays.asList("Name", "Surname","Total ills amount",
                "Department ID", "Started at","Illness")));
        model.addAttribute("fields", new ArrayList<>(Arrays.asList("name","surname","numOfIllnesses",
                "departmentId", "startedAt", "illnessName")));
        model.addAttribute("action","patients");
        return "table";
    }
}
