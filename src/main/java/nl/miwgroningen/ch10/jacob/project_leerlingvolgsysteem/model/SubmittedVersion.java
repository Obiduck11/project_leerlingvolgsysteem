package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SubmittedVersionRepository;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateSubmitted;

    @OneToOne (mappedBy = "submittedVersion", cascade = CascadeType.REMOVE)
    private Assessment assessment;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Assignment assignment;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<SubmittedVersion> submittedVersions = new HashSet<>();

    //TODO deze kan weg volgens mij.
    public String getStudentDisplayName() {
        return student.toString();
    }

    public void addSubmittedVersion(SubmittedVersionRepository submittedVersionRepository){
        for (SubmittedVersion submit : submittedVersionRepository.findAll()) {
            if(submit.getStudent().getStudentId().equals(this.getStudent().getStudentId())){
                if(submit.getAssignment().equals(this.getAssignment())){
                    this.submittedVersions.add(submit);
                    submit.submittedVersions.add(this);
                }
            }
        }

    }

    public void removeSubmittedVersion(SubmittedVersionRepository versionRepository){
        for (SubmittedVersion submittedVersion : versionRepository.findAll()) {
            if(submittedVersion.submittedVersions.contains(this)){
                submittedVersion.submittedVersions.remove(this);
                versionRepository.save(submittedVersion);
            if(this.submittedVersions.contains(submittedVersion))
                this.submittedVersions.remove(submittedVersion);
                versionRepository.save(this);
            }
        }
    }
    public String toString(){
        return String.format("%s%d", assignment.getTitle(), versionId);
    }

}
