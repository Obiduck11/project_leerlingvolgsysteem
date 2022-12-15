package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assessment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    public AssessmentController(AssessmentRepository assessmentRepository, FeedbackRepository feedbackRepository,
                                SubmittedVersionRepository submittedVersionRepository) {
        this.assessmentRepository = assessmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.submittedVersionRepository = submittedVersionRepository;
    }

    @GetMapping({"/assessments/all"})
    protected String showAssessmentOverview(Model model) {
        model.addAttribute("allAssessments", assessmentRepository.findAll());
        model.addAttribute("allFeedback", feedbackRepository.findAll());
        model.addAttribute("allSubmittedVersions", submittedVersionRepository.findAll());
        return "assessmentOverview";
    }


    private String showAssessmentForm(Model model, Assessment assessment) {
        model.addAttribute("assessment", assessment);
        model.addAttribute("allFeedback", feedbackRepository.findAll());
        return "assessmentForm";
    }


    @GetMapping("assessments/edit/{assessmentId}")
    protected String showEditAssessmentForm(@PathVariable("assessmentId") Long AssessmentId, Model model) {
        Optional<Assessment> assessment = assessmentRepository.findById(AssessmentId);

        if (assessment.isPresent()) {
            return showAssessmentForm(model, assessment.get());
        }
        return "redirect:/assessments/all";
    }

    @GetMapping("/assessments/delete/{assessmentId}")
    protected String deleteAssessment(@PathVariable("assessmentId") Long assessmentId) {
        Optional<Assessment> assessment = assessmentRepository.findById(assessmentId);

        if (assessment.isPresent()) {
            assessmentRepository.delete(assessment.get());
        }
        return "redirect:/assessments/all";
    }

    @GetMapping("/assessments/new/{versionId}")
    protected String showAssessmentFormForSubmittedVersion(@PathVariable ("versionId") Long versionId, Model model){
        Optional<SubmittedVersion> submittedVersion = submittedVersionRepository.findById(versionId);
        Assessment assessment = new Assessment();
        if(submittedVersion.isPresent()){
            assessment.setSubmittedVersion(submittedVersion.get());
        }
        return showAssessmentForm(model, assessment);
    }


    @PostMapping("/assessments/new/{versionId}")
    protected String addAssessmentToSubmittedVersion(@PathVariable ("versionId") Long versionId, @ModelAttribute ("assessment") Assessment assessment, BindingResult result){
        Optional<SubmittedVersion> submittedVersion = submittedVersionRepository.findById(versionId);
        if(submittedVersion.isPresent()){
            if(!result.hasErrors()){
                assessment.setSubmittedVersion(submittedVersion.get());
                submittedVersion.get().setAssessment(assessment);
                assessmentRepository.save(assessment);
                submittedVersionRepository.save(submittedVersion.get());
            }
        }
        return "redirect:/courses/details/id/" + submittedVersion.get().getAssignment().getCourse().getCourseId();
    }
    @RequestMapping("/test")
    public String showCheckbox(Model model) {
        boolean pass = false;
        model.addAttribute("pass", pass);
        return "assessmentForm";
    }

}


