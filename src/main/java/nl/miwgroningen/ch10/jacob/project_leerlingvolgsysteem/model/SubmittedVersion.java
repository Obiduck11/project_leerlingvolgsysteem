package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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

    public String getStudentDisplayName() {
        return student.toString();
    }



    public String toString(){
        return String.format("%s%d", assignment, versionId);
    }

}
