package nl.imine.webconsole.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.webconsole.model.ApplicationUser;
import nl.imine.webconsole.service.ApplicationUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserDetailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ApplicationUserService applicationUserService;
    private final ObjectMapper objectMapper;

    public UserDetailAuthenticationSuccessHandler(ApplicationUserService applicationUserService, ObjectMapper objectMapper) {
        this.applicationUserService = applicationUserService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ApplicationUser user = applicationUserService.findUser(authentication.getName()).orElseThrow(() -> new IllegalStateException("No user found for user that just authenticated"));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
    }
}
