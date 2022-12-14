package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.AssignmentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.StudentRepository;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SubmittedVersionRepository;
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
@Controller @RequestMapping("/submittedVersions")
public class SubmittedVersionController {

    private final SubmittedVersionRepository submittedVersionRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;


    public SubmittedVersionController(
            SubmittedVersionRepository submittedVersionRepository,
            StudentRepository studentRepository,
            AssignmentRepository assignmentRepository) {
        this.submittedVersionRepository = submittedVersionRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/all")
    protected String showSubmittedVersionsOverview(Model model) {
        model.addAttribute("allSubmittedVersions", submittedVersionRepository.findByOrderByDateSubmittedDesc());
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
            newSubmit.addSubmittedVersion(submittedVersionRepository);
            submittedVersionRepository.save(newSubmit);
        }
        return "redirect:/submittedVersions/all";
    }

//    @GetMapping("/new/{studentId}")
//    private String showNewSubmitFormPerStudent(Model model, Student student){
//        return showSubmitFormPerStudent(model, new SubmittedVersion());
//    }
//
//    private String showSubmitFormPerStudent(Model model, SubmittedVersion submittedVersion) {
//        model.addAttribute("submittedVersion", submittedVersion);
//        model.addAttribute("allAssignments", assignmentRepository.findById(submittedVersion.getAssignment().getAssignmentId()));
//        model.addAttribute("allStudents", studentRepository.findById(submittedVersion.getStudent().getStudentId()));
//        return "submittedVersions/submittedVersionForm";
//    }

//    @PostMapping("/new")
//    private String addNewInstantSubmit(@ModelAttribute("submittedVersionPerStudent") SubmittedVersion newSubmit, Student student, BindingResult result){
//        if(!result.hasErrors()) {
//
//            newSubmit.addSubmittedVersion((submittedVersionRepository));
//            submittedVersionRepository.save(newSubmit);
//        }
//        return "redirect:/assessments/new/submittedVersionId";
//
//        }


    private String showSubmitForm(Model model, SubmittedVersion submittedVersion){
        model.addAttribute("submittedVersion", submittedVersion);
        model.addAttribute("allAssignments", assignmentRepository.findAll());
        model.addAttribute("allStudents", studentRepository.findAll());
        return "submittedVersions/submittedVersionForm";
    }

    @GetMapping("/new/{studentId}/{assignmentId}")
    private String makeInstantSubmit(@PathVariable("studentId") Long studentId,
                                     @PathVariable("assignmentId") Long assignmentId, Model model){
        Optional<Student> student = studentRepository.findById(studentId);
        return "redirect:/submittedVersions/new";
        //TODO verder uitwerken om direct in te kunnen leveren en te beoordelen.
    }

    @GetMapping("/delete/{versionId}")
    private String deleteSubmission(@PathVariable ("versionId") Long versionId){
        Optional<SubmittedVersion> submittedVersion = submittedVersionRepository.findById(versionId);
        if(submittedVersion.isPresent()){
            submittedVersion.get().removeSubmittedVersion(submittedVersionRepository);
            submittedVersionRepository.delete(submittedVersion.get());
        }
        return "redirect:/submittedVersions/all";
    }
}