package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Jacob Visser
 * <p>
 * Eigenschappen van een student
 */
@Entity @Getter @Setter
public class Student {

    @Id
    @GeneratedValue
    private Long studentId;

    private String firstName;
    private String inFixName;
    private String lastName;

    private String githubAccount;

    @ManyToMany (cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Course> courses;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<SubmittedVersion> submittedVersions;

    public String getDisplayName() {
        String displayName = firstName;

        if (!inFixName.equals("")) {
            displayName += " " + inFixName;
        }

        return displayName += " " + lastName;
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public String toString() {
        return getDisplayName();
    }
}
