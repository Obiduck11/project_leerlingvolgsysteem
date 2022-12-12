package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
 */
@Entity @Getter @Setter
public class Assignment implements Comparable<Assignment> {

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




    @Override
    public int compareTo(Assignment assignment) {
        {
            if(serialNumber == assignment.serialNumber)
                return 0;
            else if (serialNumber > assignment.serialNumber)
                return 1;
            else
                return -1;
        }


    }
}
