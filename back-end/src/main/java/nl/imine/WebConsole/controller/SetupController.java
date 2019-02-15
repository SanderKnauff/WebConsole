package nl.imine.WebConsole.controller;

import nl.imine.WebConsole.dto.NewUserDto;
import nl.imine.WebConsole.model.ApplicationUser;
import nl.imine.WebConsole.model.ApplicationUserRole;
import nl.imine.WebConsole.repository.ApplicationUserRepository;
import nl.imine.WebConsole.service.ApplicationUserService;
import nl.imine.WebConsole.validator.NewUserDtoValidator;
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
    private final NewUserDtoValidator newUserDtoValidator;

    public SetupController(ApplicationUserRepository applicationUserRepository, ApplicationUserService applicationUserService, NewUserDtoValidator newUserDtoValidator) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.newUserDtoValidator = newUserDtoValidator;
    }

    @GetMapping
    public View getPage() {
        if(applicationUserRepository.count() <= 0) {
            return new ModelAndView("setup").getView();
        } else {
            return new RedirectView("/");
        }
    }

    @PostMapping
    public ModelAndView setupInitialUser(@ModelAttribute("UserDetails") NewUserDto newUserDto, BindingResult bindingResult) {
        if(applicationUserRepository.count() <= 0) {
            newUserDtoValidator.validate(newUserDto, bindingResult);

            if (!newUserDto.getPassword().equals(newUserDto.getNewPassword())) {
                bindingResult.rejectValue("newPassword", "password.not.equal");
            }

            if (bindingResult.hasErrors()) {
                return new ModelAndView("setup", "errors", bindingResult.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));
            }

            //Recreate object as down-casted type so JPA can handle it. Also removes passwordConfirm object
            applicationUserService.save(new ApplicationUser(newUserDto.getUsername(), newUserDto.getPassword(), Collections.singleton(ApplicationUserRole.ADMIN)));
            return new ModelAndView("login");
        } else {
            return new ModelAndView("/");
        }
    }
}
