package nl.imine.WebConsole.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NewUserDto {

    private final String username;
    private final String password;
    private final String newPassword;

    @JsonCreator
    public NewUserDto(String username, String password, String newPassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
