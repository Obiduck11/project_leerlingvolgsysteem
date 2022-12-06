package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Feedback;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma beheert alle feedback
 */

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping({"/all"})
    protected String showFeedbackOverview(Model model) {
        model.addAttribute("allFeedback", feedbackRepository.findAll());
        return "feedbackOverview";
    }

    @GetMapping("/new")
    protected String showNewFeedbackForm(Model model) {
        return showFeedbackForm(model, new Feedback());
    }

    private String showFeedbackForm(Model model, Feedback feedback) {
        model.addAttribute("feedback", feedback);
        return "feedbackForm";
    }

    @PostMapping("/new")
    protected String saveFeedback(@ModelAttribute("feedback") Feedback feedback, BindingResult result) {
        if(!result.hasErrors()) {
            feedbackRepository.save(feedback);
        }
        return "redirect:/feedback/all";
    }

    @GetMapping("/edit/{feedbackId}")
    protected String showEditFeedbackform(@PathVariable("feedbackId") Long feedbackId, Model model) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);

        if (feedback.isPresent()) {
            return showFeedbackForm(model, feedback.get());
        }
        return "redirect:/feedback/all";
    }

    @GetMapping("/delete/{feedbackId}")
    protected String deleteFeedback(@PathVariable("feedbackId") Long feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);

        if (feedback.isPresent()) {
            feedbackRepository.delete(feedback.get());
        }
        return "redirect:/feedback/all";
    }
}
