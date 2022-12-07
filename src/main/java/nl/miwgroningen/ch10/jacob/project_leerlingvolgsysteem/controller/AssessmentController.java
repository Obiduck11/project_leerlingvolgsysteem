package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assessment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * houdt de voortgang van de student bij in de te maken opdrachten
 */

@Controller
public class AssessmentController {
    private final AssessmentRepository assessmentRepository;
    private final FeedbackRepository feedbackRepository;
    private final SubmittedVersionRepository submittedVersionRepository;

    public AssessmentController(AssessmentRepository assessmentRepository, FeedbackRepository feedbackRepository, SubmittedVersionRepository submittedVersionRepository) {
        this.assessmentRepository = assessmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.submittedVersionRepository = submittedVersionRepository;
    }

    @GetMapping({"/assessments"})
    protected String showAssessmentOverview(Model model) {
        model.addAttribute("allAssessments", assessmentRepository.findAll());
        model.addAttribute("allFeedback", feedbackRepository.findAll());
        model.addAttribute("allSubmittedVersions", submittedVersionRepository.findAll());
        return "assessmentOverview";
    }

    @GetMapping("/assessments/new")
    protected String showNewAssessmentForm(Model model) {
        return showAssessmentForm(model, new Assessment());
    }

    private String showAssessmentForm(Model model, Assessment assessment) {
        model.addAttribute("assessment", assessment);
        model.addAttribute("allFeedback", feedbackRepository.findAll());
        return "assessmentForm";
    }

    @PostMapping("/assessments/new")
    protected String saveAssessment(@ModelAttribute ("assessment") Assessment assessment, BindingResult result) {
        if(!result.hasErrors()) {
            assessmentRepository.save(assessment);
        }
        return "redirect:/assessments";
    }

}


