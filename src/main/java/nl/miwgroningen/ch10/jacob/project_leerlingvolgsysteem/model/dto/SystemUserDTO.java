package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SystemUser;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Getter @Setter
public class SystemUserDTO {
    private Long systemUserId;
    private String username;
    private String password;
    private String passwordCheck;

    public SystemUserDTO(SystemUser systemUser) {
        systemUserId = systemUser.getSystemUserId();
        username = systemUser.getUsername();
    }

    public SystemUserDTO() {

    }

    public Optional<SystemUser> getSystemUser(PasswordEncoder passwordEncoder) {
        if (!password.equals(passwordCheck)) {
            return Optional.empty();
        }

        SystemUser systemUser = new SystemUser();
        systemUser.setSystemUserId(systemUserId);
        systemUser.setUsername(username);
        systemUser.setPassword(passwordEncoder.encode(password));

        return Optional.of(systemUser);
    }

}
