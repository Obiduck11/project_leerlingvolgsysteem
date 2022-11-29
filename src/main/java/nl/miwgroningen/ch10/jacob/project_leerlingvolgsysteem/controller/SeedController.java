package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

public class SeedController {

private final CourseRepository courseRepository;
private final StudentRepository studentRepository;

    public SeedController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

@GetMapping("/seed")
    protected String seedDatabase(){
    Course OOP = new Course();
    OOP.setName("OOP");
    OOP.setFieldOfStudy("Make IT work");

    courseRepository.save(OOP);

    Set<Course> courses = new HashSet<>();
    courses.add(OOP);

    Student jacob = new Student();
    jacob.setFirstName("Jacob");
    jacob.setInFixName("tussen");
    jacob.setLastName("Visser");
    jacob.setGithubAccount("Obiduck11");
    jacob.setCourses(courses);

    studentRepository.save(jacob);

    return "redirect:/students/all";
}

}
