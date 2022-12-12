package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssessmentRepository assessmentRepository;
    private final SubmittedVersionRepository submittedVersionRepository;


    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository, AssignmentRepository assignmentRepository, AssessmentRepository assessmentRepository, SubmittedVersionRepository submittedVersionRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.assessmentRepository = assessmentRepository;
        this.submittedVersionRepository = submittedVersionRepository;
    }

    @GetMapping ("/all")
    protected String showCourseOverview(Model model) {
        model.addAttribute("allCourses", courseRepository.findAll());

        return "courseOverview";
    }

    @GetMapping ("/new")
    protected String showNewCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allStudents", studentRepository.findAll());
        return "courseForm";
    }

    @PostMapping ("/new")
    protected String saveCourse(@ModelAttribute("course") Course courseToBeSaved, BindingResult result) {
        if (!result.hasErrors()){
            addCourseToStudents(courseToBeSaved);
            courseRepository.save(courseToBeSaved);
        }
        return "redirect:/courses/all";
    }

    protected void addCourseToStudents(Course course){
        for (Student student : course.getStudents()) {
            course.addStudent(student);
        }
    }

    @GetMapping ("/details/id/{courseId}")
    protected String showCourseDetails(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            return showDetailsForCourse(model, course);
        }

        return "redirect:/courses/all";
    }

    private String showDetailsForCourse(Model model, Optional<Course> course) {
        model.addAttribute("courseToShow", course.get());
        return "courseDetails";
    }

    @GetMapping ("/edit/id/{courseId}")
    protected String showEditCourseForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            return showEditCourse(model, course.get());
        }
        return "redirect:/courses/all";
    }

    private String showEditCourse(Model model, Course course) {
        model.addAttribute("course", course);
        model.addAttribute("allStudents", studentRepository.findAll());
        model.addAttribute("allAssignments", assignmentRepository.findAll());
        model.addAttribute("allAssessments", assessmentRepository.findAll());
        model.addAttribute("allSubmittedVersions", assessmentRepository.findAll());
        Collections.sort(course.getAssignments());

        return "courseForm";
    }

    @GetMapping("/delete/{courseId}")
    protected String deleteCourse(@PathVariable("courseId") Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent()){
            deleteCourseFromAssignment(course.get());
            deleteCourseFromStudent(course.get());
            courseRepository.delete(course.get());
        }

        return "redirect:/courses/all";
    }

    protected void deleteCourseFromStudent(Course course){
        for (Student student : studentRepository.findAll()) {
            if(course.getStudents().contains(student)){
                course.removeStudent(student);
            }
        }
    }
    protected void deleteCourseFromAssignment(Course course){
        for (Assignment assignment : course.getAssignments()) {
            assignment.setCourse(null);
            }
        }



}
