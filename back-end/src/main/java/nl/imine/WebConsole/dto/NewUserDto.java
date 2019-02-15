package nl.imine.WebConsole.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NewUserDto {

    private final String username;
    private final String password;
    private final String passwordConfirm;

    @JsonCreator
    public NewUserDto(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
}
