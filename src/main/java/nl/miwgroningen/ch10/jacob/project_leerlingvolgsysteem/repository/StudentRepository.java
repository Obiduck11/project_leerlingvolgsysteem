package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
