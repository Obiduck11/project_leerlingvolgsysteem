package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    public void addAssignment(){
        for (Assignment assignment : course.getAssignments()) {
            if(assignment.serialNumber >= this.serialNumber){
                assignment.serialNumber += 1;
            }
        }
    }
    public int count(String add){
        int count;
        if (add.equals("plus")) {
            count = 1;
        } else {
            count = -1;
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return assignmentId.equals(that.assignmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId);
    }
}
