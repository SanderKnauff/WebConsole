package nl.imine.WebConsole.service;

import nl.imine.WebConsole.model.ApplicationUser;
import nl.imine.WebConsole.model.ApplicationUserRole;
import nl.imine.WebConsole.repository.ApplicationUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findOne(username);
        if (applicationUser != null) {
            return new User(applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getRoles());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
