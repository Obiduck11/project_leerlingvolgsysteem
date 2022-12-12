package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.service;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.repository.SystemUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Robbin Drent <r.v.drent@st.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */
@Service
public class SystemUserDetailService implements UserDetailsService {
    private final SystemUserRepository systemUserRepository;

    public SystemUserDetailService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return systemUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Gebruiker met de naam" + username + " niet gevonden"));
    }
}
