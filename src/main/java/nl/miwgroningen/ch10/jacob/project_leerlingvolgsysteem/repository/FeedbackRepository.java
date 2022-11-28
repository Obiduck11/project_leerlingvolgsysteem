package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
