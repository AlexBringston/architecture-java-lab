package application.model;

import lombok.*;

import javax.persistence.*;
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

    private int numOfBeds;
    private int numOfBusyBeds;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "department")
    private List<Patient> patients;
}
