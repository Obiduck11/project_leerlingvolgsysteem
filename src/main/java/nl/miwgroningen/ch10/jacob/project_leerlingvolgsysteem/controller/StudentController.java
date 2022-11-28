package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/new")
    protected String showNewStudentForm(Model model){
        return showFormForStudent(model, new Student());
    }

    private String showFormForStudent(Model model, Student student){
        model.addAttribute("student", student);

        return "studentForm";
    }
    @PostMapping("/new")
    protected String addStudent(@ModelAttribute ("student") Student studentToAdd, BindingResult result){
            if(!result.hasErrors()){
                studentRepository.save(studentToAdd);
            }
            return "redirect:/students/all";
    }


}
