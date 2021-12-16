package application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "humans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Patient {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String name;
    private String surname;

    private Long numOfIllnesses;

    @ManyToOne
    @JoinColumn(name="department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "patient")
    private List<PatientIllness> patientIllnesses;

}
