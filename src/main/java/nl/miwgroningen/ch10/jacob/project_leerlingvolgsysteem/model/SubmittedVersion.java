package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SubmittedVersionRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Entity
@Getter @Setter
public class SubmittedVersion {

    @Id
    @GeneratedValue
    private Long versionId;

    private LocalDate dateSubmitted;

    @OneToOne (mappedBy = "submittedVersion")
    private Assessment assessment;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Assignment assignment;

    @OneToMany
    private Set<SubmittedVersion> submittedVersions =new HashSet<>();

    public String getStudentDisplayName() {
        return student.toString();
    }



    private Set<SubmittedVersion> numberOfSubmittedPerAssignmentPerStudent(SubmittedVersionRepository submittedVersionRepository){
        for (SubmittedVersion submittedVersion: submittedVersionRepository.findAll()) {
            if(submittedVersion.getStudent().equals(student)){
                if(submittedVersion.getAssignment().equals(assignment)){
                   submittedVersions.add(submittedVersion);
                }
            }
        }
        return submittedVersions;
    }

    public String toString(){
        return String.format("%s%d", assignment.getTitle(), versionId);
    }

}
