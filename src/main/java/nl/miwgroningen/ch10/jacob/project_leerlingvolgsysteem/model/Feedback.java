package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * houdt bij welke feedback gegeven wordt
 */

@Entity @Getter @Setter
public class Feedback {

    @Id
    @GeneratedValue
    private Long feedbackId;

    private String content;
    private String title;
}
