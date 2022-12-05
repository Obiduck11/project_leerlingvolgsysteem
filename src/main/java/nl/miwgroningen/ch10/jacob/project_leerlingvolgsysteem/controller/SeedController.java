package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssignmentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class SeedController {

private final CourseRepository courseRepository;
private final StudentRepository studentRepository;

private final AssignmentRepository assignmentRepository;

    public SeedController(CourseRepository courseRepository, StudentRepository studentRepository, AssignmentRepository assignmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
    }

@GetMapping("/seed")
    protected String seedDatabase(){
    Course OOP = new Course();
    OOP.setName("OOP");
    OOP.setFieldOfStudy("Make IT work");

    Course programming = new Course();
    programming.setName("Programming");
    programming.setFieldOfStudy("Make IT work");

    courseRepository.save(OOP);
    courseRepository.save(programming);

    Set<Course> courses= new HashSet<>();
    courses.add(OOP);
    courses.add(programming);

    Student jacob = new Student();
    jacob.setFirstName("Dummy");
    jacob.setInFixName("tussen");
    jacob.setLastName("Dommy");
    jacob.setGithubAccount("Obiduck11");
    jacob.setCourses(courses);

    Student dommy = new Student();
    dommy.setFirstName("dommy2");
    dommy.setInFixName("van");
    dommy.setLastName("test");
    dommy.setGithubAccount("narnja");
    dommy.setCourses(courses);

    Set<Student> students = new HashSet<>();
    students.add(jacob);
    students.add(dommy);

    studentRepository.save(jacob);
    studentRepository.save(dommy);

    Assignment verslag = new Assignment();
    verslag.setTitle("reflectie");
    verslag.setCourse(programming);
    verslag.setDescription("Dit verslag is bedoeld om na te denken over de zin en onzin van programming");
    verslag.setSerialNumber(1);

    assignmentRepository.save(verslag);

    OOP.setStudents(students);


    return "redirect:/students/all";
}

}
