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
 * De eigenschappen van een student
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

    @OneToMany(mappedBy = "student", cascade = {CascadeType.REMOVE})
    @OrderBy("dateSubmitted")
    private Set<SubmittedVersion> submittedVersions;

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

    public String getDisplayName() {
        String displayName = firstName;

        if (!inFixName.equals("")) {
            displayName += " " + inFixName;
        }

        return displayName + (" " + lastName);
    }

    public String getCoursesToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Course course : courses) {
            stringBuilder . append(String.format("%s<br />", course.getName()));
        }

        return stringBuilder.toString();
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

    public List<Assignment> studentInCourse (Student student) {
        List<Assignment> assignmentList = new ArrayList<>();

        for (Course course : student.getCourses()) {
            for (Assignment assignment : course.getAssignments()) {
                assignmentList.add(assignment);
            }
        }
        return assignmentList;
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public boolean passedPreviousAssignment(Assignment assignment) {
        int index = assignment.getCourse().getAssignments().indexOf(assignment);
            for (SubmittedVersion submittedVersion : submittedVersions) {
                if (index != 0) {
                    Assignment previousAssignment = assignment.getCourse().getAssignments().get(index - 1);
                    if (submittedVersion.getAssignment().equals(previousAssignment)) {
                        if (checkIfSubmitHasAssessment(submittedVersion)) {
                            if(submittedVersion.getAssessment().isPass())
                                return true;
                        }
                    }
                }
            }
        return false;
    }

    public boolean checkIfSubmitHasAssessment(SubmittedVersion submittedVersion){
        if(submittedVersion.getAssessment() == null){
            return false;
        }
        return true;
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

    public boolean checkForUnassessedSubmit(Assignment assignment){
        for (SubmittedVersion submittedVersion : versionsPerAssignment(assignment)) {
            if(!checkIfSubmitHasAssessment(submittedVersion)){
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return getDisplayName();
    }
}
