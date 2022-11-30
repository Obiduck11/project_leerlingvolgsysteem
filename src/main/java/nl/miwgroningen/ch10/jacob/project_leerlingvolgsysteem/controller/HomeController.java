package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */

@Controller
public class HomeController {

    @GetMapping({"/home", "/"})
        protected String showHomepage() {
        return "home";
    }
}
