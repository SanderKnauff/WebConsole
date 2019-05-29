package nl.imine.webconsole.controller;

import nl.imine.webconsole.model.ApplicationUser;
import nl.imine.webconsole.repository.ApplicationUserRepository;
import nl.imine.webconsole.service.ApplicationUserService;
import nl.imine.webconsole.validator.NewUserDtoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserPageController {

    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserService applicationUserService;
    private final NewUserDtoValidator newUserDtoValidator;

    public UserPageController(ApplicationUserRepository applicationUserRepository, ApplicationUserService applicationUserService, NewUserDtoValidator newUserDtoValidator) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.newUserDtoValidator = newUserDtoValidator;
    }

    @GetMapping
    public List<ApplicationUser> getPage() {
        return cleanUsers(applicationUserRepository.findAll());
    }


    @PostMapping("/new")
    public ResponseEntity createNewUser(@RequestBody @Valid NewUserDto newUserDto, BindingResult bindingResult) {
        newUserDtoValidator.validate(newUserDto, bindingResult);

        if (!newUserDto.getPassword().equals(newUserDto.getNewPassword())) {
            bindingResult.rejectValue("newPassword", "password.not.equal");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));
        }

        //Recreate object as down-casted type so JPA can handle it. Also removes passwordConfirm object
        applicationUserService.save(new ApplicationUser(newUserDto.getUsername(), newUserDto.getPassword(), newUserDto.getRoles()));
        return ResponseEntity.ok(cleanUser(newUserDto));

    }

    private List<ApplicationUser> cleanUsers(List<ApplicationUser> users) {
        return users.stream()
                .peek(this::cleanUser)
                .collect(Collectors.toList());
    }

    private ApplicationUser cleanUser(ApplicationUser applicationUser) {
        applicationUser.setPassword("");
        return applicationUser;
    }

    private static class NewUserDto extends ApplicationUser {

        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
