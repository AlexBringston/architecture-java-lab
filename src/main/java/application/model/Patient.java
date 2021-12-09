package application.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Long departmentId;

}
