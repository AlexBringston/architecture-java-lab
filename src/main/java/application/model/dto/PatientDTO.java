package application.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PatientDTO {

    private String name;
    private String surname;
    private Long numOfIllnesses;
    private Long departmentId;
    private LocalDate startedAt;
    private String illnessName;
    private Integer age;
    private String phone;

    public PatientDTO(String name, String surname, Long numOfIllnesses, Long departmentId, LocalDate startedAt, String illnessName, Integer age, String phone) {
        this.name = name;
        this.surname = surname;
        this.numOfIllnesses = numOfIllnesses;
        this.departmentId = departmentId;
        this.startedAt = startedAt;
        this.illnessName = illnessName;
        this.age = age;
        this.phone = phone;
    }
}
