package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
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

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping ("/all")
    protected String showCourseOverview(Model model) {
        model.addAttribute("allCourses", courseRepository.findAll());

        return "courseOverview";
    }

    @GetMapping ("/new")
    protected String showNewCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allCourses", courseRepository.findAll());
        return "courseForm";
    }

    @PostMapping ("/new")
    protected String saveCourse(@ModelAttribute("book") Course courseToBeSaved, BindingResult result) {
        if (!result.hasErrors()){
            courseRepository.save(courseToBeSaved);
        }

        return "redirect:/courses/all";
    }

    @GetMapping ("/details/id/{courseId}")
    protected String showCourseDetails(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            return showDetailsForCourse(model, course);
        }

        return "redirect:/courses/all";
    }

    private static String showDetailsForCourse(Model model, Optional<Course> course) {
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
        return "courseForm";
    }

}
