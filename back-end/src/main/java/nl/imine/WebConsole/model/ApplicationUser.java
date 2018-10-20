package nl.imine.WebConsole.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class ApplicationUser {

    @Id
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ApplicationUserRole> roles;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, Set<ApplicationUserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ApplicationUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ApplicationUserRole> roles) {
        this.roles = roles;
    }
}
