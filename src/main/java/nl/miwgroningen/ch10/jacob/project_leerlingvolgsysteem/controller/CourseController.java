package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
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
        if(course.getStudents() != null) {
            for (Student student : course.getStudents()) {
                if (!course.getStudents().contains(student)) {
                    course.addStudent(student);
                }
            }
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

    @GetMapping("/editOrderAssignment/{courseId}/{assignmentId}")
    protected String editOrder(@PathVariable("courseId") Long courseId, @PathVariable("assignmentId") Long assignmentId, @RequestParam String add){
        Optional <Course> courseToEdit = courseRepository.findById(courseId);
        Assignment assignmentToReplace = new Assignment();

        for (Assignment assignment : courseToEdit.get().getAssignments()) {
            if(assignment.getAssignmentId().equals(assignmentId)){
                assignmentToReplace = assignment;
            }
        }
           courseToEdit.get().setAssignments(courseToEdit.get().editAssignmentOrder(assignmentToReplace, count(add)));
           courseRepository.save(courseToEdit.get());
        return  "redirect:/courses/details/id/" + courseId;
    }

    private int count(String add){
        int count;
        if (add.equals("plus")) {
            count = 1;
        } else {
            count = -1;
        }
        return count;
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
