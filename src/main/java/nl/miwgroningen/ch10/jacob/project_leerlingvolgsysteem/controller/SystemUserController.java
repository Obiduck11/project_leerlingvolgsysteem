package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.controller;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SystemUser;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.dto.SystemUserDTO;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Controller
@RequestMapping("/user")
public class SystemUserController {
    private final PasswordEncoder passwordEncoder;
    private final SystemUserRepository systemUserRepository;

    public SystemUserController(PasswordEncoder passwordEncoder, SystemUserRepository systemUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.systemUserRepository = systemUserRepository;
    }

    @GetMapping("/new")
    protected String showNewUserForm(Model model) {
        model.addAttribute("systemUser", new SystemUserDTO());
        return "userForm";
    }

    @PostMapping("/new")
    protected String saveOrUpdateUser(@ModelAttribute("systemUser") SystemUserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "userForm";
        }

        Optional<SystemUser> user = userDTO.getSystemUser(passwordEncoder);

        if (user.isEmpty()) {
            return "userForm";
        }

        systemUserRepository.save(user.get());
        return "redirect:/";

    }
}
