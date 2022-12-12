package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
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


}


