package nl.imine.WebConsole.controller;

import nl.imine.WebConsole.dto.NewUserDto;
import nl.imine.WebConsole.model.ApplicationUser;
import nl.imine.WebConsole.repository.ApplicationUserRepository;
import nl.imine.WebConsole.service.ApplicationUserService;
import nl.imine.WebConsole.validator.NewUserDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
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

    @PostMapping
    public ResponseEntity<?> setupInitialUser(@RequestBody NewUserDto newUserDto, BindingResult bindingResult) {
        if(applicationUserRepository.count() <= 0) {
            newUserDtoValidator.validate(newUserDto, bindingResult);

            if (!newUserDto.getPassword().equals(newUserDto.getPasswordConfirm())) {
                bindingResult.rejectValue("newPassword", "password.not.equal");
            }

            if (bindingResult.hasErrors()) {
                List<String> validationResults = bindingResult.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList());
                return ResponseEntity.badRequest().body(validationResults);
            }

            //Recreate object as down-casted type so JPA can handle it. Also removes passwordConfirm object
            applicationUserService.save(new ApplicationUser(newUserDto.getUsername(), newUserDto.getPassword(), Collections.emptySet()));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
