package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssessmentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssignmentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SubmittedVersionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Controller @RequestMapping("/submittedVersions")
public class SubmittedVersionController {

    private final SubmittedVersionRepository submittedVersionRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssessmentRepository assessmentRepository;

    public SubmittedVersionController(
            SubmittedVersionRepository submittedVersionRepository,
            StudentRepository studentRepository,
            AssignmentRepository assignmentRepository, AssessmentRepository assessmentRepository) {
        this.submittedVersionRepository = submittedVersionRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @GetMapping("/all")
    protected String showSubmittedVersionsOverview(Model model) {
        model.addAttribute("allSubmittedVersions", submittedVersionRepository.findAll());
        return "submittedVersions/submittedVersionsOverview";
    }

    @GetMapping("/details/{versionId}")
    protected String showSubmittedVersionDetails(@PathVariable("versionId") Long versionId, Model model) {
        Optional<SubmittedVersion> submittedVersion = submittedVersionRepository.findById(versionId);

        if (submittedVersion.isPresent()) {
            return showDetailsForSubmittedVersion(model, submittedVersion);
        }
        return "redirect:/submittedVersions/all";
    }

    private static String showDetailsForSubmittedVersion(Model model, Optional<SubmittedVersion> submittedVersion) {
        model.addAttribute("submittedVersionToShow", submittedVersion.get());
        return "submittedVersions/submittedVersionDetails";
    }

    @GetMapping("/submittedVersionsPerStudent/{studentId}")
    protected String showSubmittedPerStudent(@PathVariable("studentId") Long studentId, Model model){
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()){
            return showVersionsPerStudent(model, student);
        }

        return "redirect:/students/all";
    }
    private String showVersionsPerStudent(Model model, Optional<Student> student) {
        model.addAttribute("studentToShow", student.get());
        return "/submittedVersions/submittedVersionsPerStudent";
    }
    @GetMapping("/new")
    private String showNewSubmitForm(Model model){
        return showSubmitForm(model, new SubmittedVersion());
    }

    @PostMapping("/new")
    private String addNewSubmittedVersion(@ModelAttribute("submittedVersion") SubmittedVersion newSubmit, BindingResult result) {
        if(!result.hasErrors()){
            submittedVersionRepository.save(newSubmit);
            System.out.println(newSubmit.getVersionId());
            newSubmit.setSubmittedVersions(sameAssignmentStudent(newSubmit));
            submittedVersionRepository.save(newSubmit);
        }
        return "redirect:/submittedVersions/all";
    }

    private Set<SubmittedVersion> sameAssignmentStudent(SubmittedVersion newSubmit){
        Set<SubmittedVersion> submittedVersions = new HashSet<>();
        for (SubmittedVersion submit : submittedVersionRepository.findAll()) {
            if(submit.getStudent().getStudentId().equals(newSubmit.getStudent().getStudentId())){
                if(submit.getAssignment().equals(newSubmit.getAssignment())){
                    submittedVersions.add(submit);
                }
            }
        }
        return submittedVersions;
    }


    private String showSubmitForm(Model model, SubmittedVersion submittedVersion){
        model.addAttribute("submittedVersion", submittedVersion);
        model.addAttribute("allAssignments", assignmentRepository.findAll());
        model.addAttribute("allStudents", studentRepository.findAll());
        return "submittedVersions/submittedVersionForm";
    }

    @GetMapping("/delete/{versionId}")
    private String deleteSubmission(@PathVariable ("versionId") Long versionId){
        Optional<SubmittedVersion> submittedVersion = submittedVersionRepository.findById(versionId);
        if(submittedVersion.isPresent()){
            submittedVersionRepository.delete(submittedVersion.get());
        }
        return "redirect:/submittedVersions/all";
    }



}