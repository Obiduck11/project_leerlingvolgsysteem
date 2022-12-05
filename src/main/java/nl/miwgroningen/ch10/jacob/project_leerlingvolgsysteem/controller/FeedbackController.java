package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Feedback;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma geeft toegang tot alle feedback
 */

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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
        return "redirect:/feedback";
    }
}
