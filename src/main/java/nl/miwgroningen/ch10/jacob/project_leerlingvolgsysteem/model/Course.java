package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany (mappedBy = "course")
    private Set<Assignment> assignments;

    @ManyToMany
    private Set<Student> students;


}
