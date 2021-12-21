package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients_illnesses")
public class PatientIllness {

    @EmbeddedId
    private PatientIllnessKey key;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("illnessId")
    @JoinColumn(name = "illness_id")
    private Illness illness;

    private Long illnessStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

}
