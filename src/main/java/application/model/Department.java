package application.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Department {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Min(0)
    private int numOfBeds;
    @Min(0)
    private int numOfBusyBeds;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "department")
    private List<Patient> patients;
}
