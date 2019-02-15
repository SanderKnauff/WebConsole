package nl.imine.WebConsole.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.imine.WebConsole.model.ApplicationUser;
import nl.imine.WebConsole.repository.ApplicationUserRepository;
import nl.imine.WebConsole.service.ApplicationUserService;
import nl.imine.WebConsole.validator.NewUserDtoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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
    public ModelAndView getPage() throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("user/userList");
        modelAndView.addObject("users", cleanUsers(applicationUserRepository.findAll()));
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView getNewUserPage() {
        return new ModelAndView("user/newUser");
    }

    @PostMapping("/new")
    public ModelAndView createNewUser(@RequestBody @Valid NewUserDto newUserDto, BindingResult bindingResult) throws JsonProcessingException {
        newUserDtoValidator.validate(newUserDto, bindingResult);

        if (!newUserDto.getPassword().equals(newUserDto.getNewPassword())) {
            bindingResult.rejectValue("newPassword", "password.not.equal");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/newUser", "errors", bindingResult.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));
        }

        //Recreate object as down-casted type so JPA can handle it. Also removes passwordConfirm object
        applicationUserService.save(new ApplicationUser(newUserDto.getUsername(), newUserDto.getPassword(), newUserDto.getRoles()));
        ModelAndView page = getPage();
        page.addObject("createdUser", cleanUser(newUserDto));
        return page;
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
