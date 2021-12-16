package application.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PatientIllnessKey implements Serializable {
    private int humanId;
    private int illnessId;
}
