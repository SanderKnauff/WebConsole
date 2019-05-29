package nl.imine.webconsole.validator;

import nl.imine.webconsole.dto.NewUserDto;
import nl.imine.webconsole.repository.ApplicationUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewUserDtoValidator implements Validator {

    private final ApplicationUserRepository applicationUserRepository;

    public NewUserDtoValidator(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewUserDto newUser = (NewUserDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "passwordConfirm.empty");

        if(applicationUserRepository.existsById(newUser.getUsername())) {
            errors.rejectValue("username", "username.exists");
        }
    }
}
