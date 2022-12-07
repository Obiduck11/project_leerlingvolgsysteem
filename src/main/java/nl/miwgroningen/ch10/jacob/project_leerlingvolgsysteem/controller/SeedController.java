package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.*;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class SeedController {

private final CourseRepository courseRepository;
private final StudentRepository studentRepository;

private final AssignmentRepository assignmentRepository;

private final FeedbackRepository feedbackRepository;

private final SubmittedVersionRepository submittedVersionRepository;

private final AssessmentRepository assessmentRepository;
    public SeedController(CourseRepository courseRepository, StudentRepository studentRepository, AssignmentRepository assignmentRepository, FeedbackRepository feedbackRepository, SubmittedVersionRepository submittedVersionRepository, AssessmentRepository assessmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.submittedVersionRepository = submittedVersionRepository;
        this.assessmentRepository = assessmentRepository;
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

    Student student1 = new Student();
    student1.setFirstName("Wytse");
    student1.setInFixName("");
    student1.setLastName("Boiten");
    student1.setGithubAccount("Harrinator");
    student1.setCourses(courses);

    Student student2 = new Student();
    student2.setFirstName("Hasna");
    student2.setInFixName("van");
    student2.setLastName("Cook");
    student2.setGithubAccount("Cookbob");
    student2.setCourses(courses);

    Student student3 = new Student();
    student3.setFirstName("Stina");
    student3.setInFixName("van der");
    student3.setLastName("Snyder");
    student3.setGithubAccount("EssEmOh");
    student3.setCourses(courses);

    Set<Student> students = new HashSet<>();
    Collections.addAll(students, student1, student2, student3);


    studentRepository.save(student1);
    studentRepository.save(student2);
    studentRepository.save(student3);

    Assignment verslag = new Assignment();
    verslag.setTitle("reflectie");
    verslag.setCourse(programming);
    verslag.setDescription("Dit verslag is bedoeld om na te denken over de zin en onzin van programming");
    verslag.setSerialNumber(1);

    Assignment werkstuk = new Assignment();
    werkstuk.setTitle("werkstuk");
    werkstuk.setCourse(programming);
    werkstuk.setDescription("mooi werkstuk over programming maken");
    werkstuk.setSerialNumber(2);

    Assignment practicum = new Assignment();
    practicum.setTitle("werkstuk");
    practicum.setCourse(programming);
    practicum.setDescription("Oefenen met variabelen in Java.");
    practicum.setSerialNumber(3);



    assignmentRepository.save(verslag);
    assignmentRepository.save(werkstuk);
    assignmentRepository.save(practicum);

    Set<Assignment> assignments = new HashSet<>();
    Collections.addAll(assignments, verslag,werkstuk,practicum);


    programming.setAssignments(assignments);
    OOP.setStudents(students);

    Feedback feedback1 = new Feedback();
    feedback1.setTitle("SABOR");
    feedback1.setContent("geen spaties");

    Feedback feedback2 = new Feedback();
    feedback2.setTitle("Indentie");
    feedback2.setContent("te weinig indentie, zo is er geen overzicht");

    Feedback feedback3 = new Feedback();
    feedback3.setTitle("Magic numbers");
    feedback3.setContent("onverklaarbare cijfers zijn niet goed te lezen voor je collega's");

    Set<Feedback> feedbacks = new HashSet<>();
    Collections.addAll(feedbacks, feedback1, feedback2, feedback3);

    feedbackRepository.saveAll(feedbacks);

    SubmittedVersion submittedVersion1 = new SubmittedVersion();
    submittedVersion1.setAssignment(verslag);
    submittedVersion1.setDateSubmitted(LocalDate.of(2022,9,11));
    submittedVersion1.setStudent(student1);


    SubmittedVersion submittedVersion2 = new SubmittedVersion();
    submittedVersion2.setAssignment(practicum);
    submittedVersion2.setDateSubmitted(LocalDate.of(2022,5,3));
    submittedVersion2.setStudent(student2);

    SubmittedVersion submittedVersion3 = new SubmittedVersion();
    submittedVersion3.setAssignment(werkstuk);
    submittedVersion3.setDateSubmitted(LocalDate.of(2022, 4, 12));
    submittedVersion3.setStudent(student3);


    submittedVersionRepository.save(submittedVersion1);
    submittedVersionRepository.save(submittedVersion2);
    submittedVersionRepository.save(submittedVersion3);


    Assessment assessment1 = new Assessment();
    assessment1.setPass(false);
    assessment1.setFeedbacks(feedbacks);
    assessment1.setSpecifiekeFeedback("goed gedaan");
    assessment1.setSubmittedVersion(submittedVersion1);

    Assessment assessment2 = new Assessment();
    assessment2.setPass(true);
    assessment2.setFeedbacks(feedbacks);
    assessment2.setSpecifiekeFeedback("minder goed gedaan");
    assessment2.setSubmittedVersion(submittedVersion2);

    assessmentRepository.save(assessment1);
    assessmentRepository.save(assessment2);




    return "redirect:/students/all";
}

}
