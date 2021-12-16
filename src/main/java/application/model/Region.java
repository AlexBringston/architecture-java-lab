package application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "regions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Region {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    @OneToMany(mappedBy = "region")
    List<Department> departments;
}
