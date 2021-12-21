package application.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Patient {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @NotNull
    @Size(min = 2, max = 20)
    private String surname;

    @NotNull
    @Column(name = "num_of_illnesses")
    private Long numOfIllnesses;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "patient")
    private List<PatientIllness> patientIllnesses;

    @NotNull
    @Min(0)
    private Integer age;

    @NotNull
    @Size(min = 10, max = 10)
    private String phoneNumber;
}
