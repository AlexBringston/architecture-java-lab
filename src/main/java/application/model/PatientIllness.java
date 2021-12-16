package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "humans_illnesses")
public class PatientIllness {

    @EmbeddedId
    private PatientIllnessKey key;

    @ManyToOne
    @MapsId("humanId")
    @JoinColumn(name = "human_id")
    private Patient patient;

    @ManyToOne
    @MapsId("illnessId")
    @JoinColumn(name = "illness_id")
    private Illness illness;

    private Long illnessStatus;

    private LocalDate startedAt;

}
