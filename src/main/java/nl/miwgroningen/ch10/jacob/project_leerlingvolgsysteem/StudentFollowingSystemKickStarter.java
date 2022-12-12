package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SystemUser;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SystemUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@SpringBootApplication
public class StudentFollowingSystemKickStarter implements CommandLineRunner {
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentFollowingSystemKickStarter(SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        if (systemUserRepository.findAll().size() == 0) {
            SystemUser user = new SystemUser();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            systemUserRepository.save(user);
        }
    }
}
