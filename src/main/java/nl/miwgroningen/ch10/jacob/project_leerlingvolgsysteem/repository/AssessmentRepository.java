package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assessment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
