package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;


    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

}
