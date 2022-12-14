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
    private String result;

    private String specifiekeFeedback;


    @ManyToMany
    private Set<Feedback> feedbacks;

    @OneToOne
    private SubmittedVersion submittedVersion;

    public String passOrFail(){

        if(pass) {
            result = "Gehaald";
        } else {
            result = "Niet gehaald";
        }
        return result;
    }



    public String getFeedbackLines() {
        String feedbackLines = "";

        for (Feedback feedback : feedbacks) {
            feedbackLines += String.format(" - %s<br />", feedback);
        }

        return feedbackLines;
    }

}
