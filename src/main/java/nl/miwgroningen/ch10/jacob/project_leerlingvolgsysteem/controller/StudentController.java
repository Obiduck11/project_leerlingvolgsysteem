package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: Jacob Visser
 * <p>
 * beheert functionaliteit betreft de eigenschappen van een student
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    @GetMapping("/all")
    protected String showAll(Model model){
        model.addAttribute("allStudents", studentRepository.findAll());

        return "studentOverview";
    }
    @GetMapping("/edit/{studentId}")
    protected String editStudent(@PathVariable("studentId") Long studentId, Model model){
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()){
            model.addAttribute("studentToShowDetailsFor", student.get());
            return showFormForStudent(model, student.get());
        }
        return "redirect:/students/all";
    }

    private String showFormForStudent(Model model, Student student){
        model.addAttribute("student", student);
        model.addAttribute("allCourses", courseRepository.findAll());

        return "studentForm";
    }

    @GetMapping("/new")
    protected String showNewStudentForm(Model model){
        return showFormForStudent(model, new Student());
    }

    @PostMapping("/new")
    protected String addStudent(@ModelAttribute ("student") Student studentToAdd, BindingResult result){
            if(!result.hasErrors()){
                studentRepository.save(studentToAdd);
            }
            return "redirect:/students/all";
    }
    @GetMapping("/details/{studentId}")
    protected String showStudentDetail(@PathVariable("studentId") Long studentId, Model model){
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()){
            model.addAttribute("studentToShowDetailsFor", student.get());
            return "studentDetail";
        }

        return "redirect:/students/all";
    }

    @GetMapping("/delete/{studentId}")
    protected String deleteStudent(@PathVariable("studentId") Long studentId){
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()){

            deleteStudentFromCourse(student.get());
            studentRepository.delete(student.get());
        }

        return "redirect:/students/all";
    }

    protected void deleteStudentFromCourse(Student student){
        for (Course course : courseRepository.findAll()) {
            if(student.getCourses().contains(course)){
                student.removeCourse(course);
            }
        }
    }
}
