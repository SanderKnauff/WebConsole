package nl.imine.WebConsole.controller;

import nl.imine.WebConsole.model.ApplicationUser;
import nl.imine.WebConsole.model.ApplicationUserRole;
import nl.imine.WebConsole.repository.ApplicationUserRepository;
import nl.imine.WebConsole.service.ApplicationUserService;
import nl.imine.WebConsole.validator.ApplicationUserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/setup")
public class SetupController {

    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserService applicationUserService;
    private final ApplicationUserValidator applicationUserValidator;

    public SetupController(ApplicationUserRepository applicationUserRepository, ApplicationUserService applicationUserService, ApplicationUserValidator applicationUserValidator) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.applicationUserValidator = applicationUserValidator;
    }

    @GetMapping
    public View getPage() {
        //If there is already a user account, don't allow to use set-up
        if(applicationUserRepository.count() <= 0) {
            return new ModelAndView("setup").getView();
        } else {
            return new RedirectView("/");
        }
    }

    @PostMapping
    public ModelAndView setupInitialUser(@ModelAttribute("UserDetails") NewApplicationUser applicationUser, BindingResult bindingResult) {
        if(applicationUserRepository.count() <= 0) {
            applicationUserValidator.validate(applicationUser, bindingResult);

            if (!applicationUser.getPassword().equals(applicationUser.getNewPassword())) {
                bindingResult.rejectValue("newPassword", "password.not.equal");
            }

            if (bindingResult.hasErrors()) {
                return new ModelAndView("setup", "errors", bindingResult.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));
            }

            applicationUser.setRoles(Collections.singleton(ApplicationUserRole.ROLE_ADMIN));

            //Recreate object as down-casted type so JPA can handle it. Also removes passwordConfirm object
            applicationUserService.save(new ApplicationUser(applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getRoles()));
            return new ModelAndView("login");
        } else {
            return new ModelAndView("/");
        }
    }

    private static class NewApplicationUser extends ApplicationUser {

        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
