package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
public interface SubmittedVersionRepository extends JpaRepository<SubmittedVersion, Long> {

    List<SubmittedVersion> findByOrderByDateSubmittedDesc();
}
