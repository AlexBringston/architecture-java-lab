package application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "illnesses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Illness {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "illness")
    private List<PatientIllness> patientIllnesses;
}
