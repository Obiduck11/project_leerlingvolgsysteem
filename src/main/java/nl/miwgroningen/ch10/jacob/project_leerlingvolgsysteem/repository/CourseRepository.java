package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
