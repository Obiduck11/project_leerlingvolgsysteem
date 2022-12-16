package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: Jacob Visser
 * <p>
 * Contains data about a course.
 */
@Entity @Getter @Setter
public class Course {

    @Id
    @GeneratedValue
    private Long courseId;

    private String name;
    private String fieldOfStudy;

    @OneToMany (mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("serialNumber")
    private List<Assignment> assignments;

    @ManyToMany (mappedBy = "courses", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OrderBy("lastName")
    private Set<Student> students;

    public void removeStudent(Student student){
        students.remove(student);
        student.getCourses().remove(this);
    }

    public void addStudent(Student student){
        students.add(student);
        student.getCourses().add(this);
    }

    public List<Assignment> editAssignmentOrder(Assignment assignment, int count){
        List<Assignment> assignmentsInNewOrder = new ArrayList<>();
        int memory = assignment.getSerialNumber();
        int newIndex = memory + count;
        for (Assignment task : assignments) {
            if(task.getSerialNumber() == newIndex){
                task.setSerialNumber(memory);
            }
            assignmentsInNewOrder.add(task);
        }
        assignment.setSerialNumber(newIndex);
        assignmentsInNewOrder.add(assignment);
        return assignmentsInNewOrder;
    }

}


