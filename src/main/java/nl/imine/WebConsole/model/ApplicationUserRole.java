package nl.imine.WebConsole.model;

import org.springframework.security.core.GrantedAuthority;

public enum ApplicationUserRole implements GrantedAuthority {

    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
