package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssignmentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
 */

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public AssignmentController(AssignmentRepository assignmentRepository, CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/all")
    protected String showAllAssignments(Model model){
        model.addAttribute("allAssignments", assignmentRepository.findAll());

        return "assignments/assignmentOverview";
    }

    @GetMapping("/new")
    protected String showNewAssignmentForm(Model model){
        return showAssignmentForm(model, new Assignment());
    }

    @PostMapping("/new")
    protected String addAssignment(@ModelAttribute("assignment") Assignment assignmentToAdd, BindingResult result){
      if(!result.hasErrors()){

          assignmentRepository.save(assignmentToAdd);
      }

      if(result.hasErrors()){
          System.out.println(result.getFieldError().toString());
      }

        return "redirect:/assignments/all";
    }

    private String showAssignmentForm(Model model, Assignment assignment){
        model.addAttribute("assignment", assignment);
        model.addAttribute("allCourses", courseRepository.findAll());

        return "/assignments/assignmentForm";
    }

    @GetMapping("/details/{assignmentId}")
    protected String showDetailsAssignment(@PathVariable("assignmentId") Long assignmentId, Model model){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);

        if(assignment.isPresent()){
            model.addAttribute("assignmentToShowDetailsFor", assignment.get());
            return "/assignments/assignmentDetail";
        }

        return "redirect:/assignments/all";
    }

    @GetMapping("/delete/{assignmentId}")
    protected String deleteStudent(@PathVariable("assignmentId") Long assignmentId){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);

        if(assignment.isPresent()){
            assignmentRepository.delete(assignment.get());
        }

        return "redirect:/assignments/all";
    }
    @GetMapping("/edit/{assignmentId}")
    protected String editAssignment(@PathVariable("assignmentId") Long assignmentId, Model model){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);

        if(assignment.isPresent()){
            model.addAttribute("assignmentToEdit", assignment );
            return showAssignmentForm(model, assignment.get());
        }
        return "/assignments/all";

    }

}
