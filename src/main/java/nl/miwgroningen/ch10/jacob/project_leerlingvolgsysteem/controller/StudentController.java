package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
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


    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    @GetMapping("/details/{studentId}")
    protected String showStudentDetail(@PathVariable("studentId") Long studentId, Model model){
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()){
            model.addAttribute("studentToShowDetailsFor", student.get());
            return "studentDetail";
        }

        return "redirect:/students/all";
    }

}