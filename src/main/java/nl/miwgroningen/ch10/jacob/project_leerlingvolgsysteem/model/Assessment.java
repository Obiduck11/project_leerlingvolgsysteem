package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * houdt beoordeling opdracht bij
 */

@Entity
@Getter
@Setter
public class Assessment {
    @Id
    @GeneratedValue
    private Long assessmentId;

    private boolean pass;
    private String specifiekeFeedback;

    @ManyToMany
    private Set<Feedback> feedbacks;

    @OneToOne
    private SubmittedVersion submittedVersion;


}
