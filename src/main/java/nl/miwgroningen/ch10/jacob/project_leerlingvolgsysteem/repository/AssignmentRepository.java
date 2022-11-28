package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
