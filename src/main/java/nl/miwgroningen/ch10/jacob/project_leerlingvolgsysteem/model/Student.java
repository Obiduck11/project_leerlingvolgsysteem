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

    public Student(String firstName, String inFixName, String lastName) {
        this.firstName = firstName;
        this.inFixName = inFixName;
        this.lastName = lastName;
    }

    public Student(String firstname, String lastName) {
        this(firstname, "", lastName);
    }

    public Student() {
        this("", "", "");

    }

    @ManyToMany (cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Course> courses;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.REMOVE})
    @OrderBy("dateSubmitted")
    private Set<SubmittedVersion> submittedVersions;

    public String getDisplayName() {
        String displayName = firstName;

        if (!inFixName.equals("")) {
            displayName += " " + inFixName;
        }

        return displayName + (" " + lastName);
    }

    public String getCoursesToString() {
        String allCourses = "";

        for (Course course : courses) {
            allCourses += String.format("%s<br />", course.getName());
        }

        return allCourses;
    }

    public List<SubmittedVersion> versionsPerAssignment (Assignment assignment) {
        List<SubmittedVersion> versionList = new ArrayList<>();

        for (SubmittedVersion submittedVersion : submittedVersions) {
            if (submittedVersion.getAssignment().equals(assignment)) {
                versionList.add(submittedVersion);
            }

        }
        return versionList;
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public boolean passedPreviousAssignment(Assignment assignment) {
        int index = assignment.getCourse().getAssignments().indexOf(assignment);
        for (SubmittedVersion submittedVersion : submittedVersions) {
            if(index != 0) {
            Assignment previousAssignment = assignment.getCourse().getAssignments().get(index - 1);
                if (submittedVersion.getAssignment().equals(previousAssignment)) {
                    if(submittedVersion.getAssessment().isPass()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean assignmentPassed(Assignment assignment) {
        for (SubmittedVersion submittedVersion: versionsPerAssignment(assignment)) {
            if (submittedVersion.getAssessment() != null) {
                if (submittedVersion.getAssessment().isPass()) {
                    return true;
                }
            }
        }
        return false;
    }
    public String toString() {
        return getDisplayName();
    }
}
