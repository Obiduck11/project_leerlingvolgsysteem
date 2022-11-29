package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssignmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
 */

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/all")
    protected String showAllAssignments(Model model){
        model.addAttribute("allAssignments", assignmentRepository.findAll());

        return "Assignments/assignmentOverview";


    }


}
