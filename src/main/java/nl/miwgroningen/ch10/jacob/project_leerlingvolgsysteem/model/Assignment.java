package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
 */
@Entity @Getter @Setter
public class Assignment {

    @Id
    @GeneratedValue
    private Long assignmentId;

    private String title;
    private String description;
    private int serialNumber;

    @ManyToOne
    private Course course;




    public String getTitle() {
        return title;
    }


}
