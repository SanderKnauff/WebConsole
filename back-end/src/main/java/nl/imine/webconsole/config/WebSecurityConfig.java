package nl.imine.webconsole.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailAuthenticationSuccessHandler userDetailAuthenticationSuccessHandler;

    public WebSecurityConfig(UserDetailAuthenticationSuccessHandler userDetailAuthenticationSuccessHandler) {
        this.userDetailAuthenticationSuccessHandler = userDetailAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Permissions
        http.authorizeRequests()
                .antMatchers("/**").permitAll();

        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.formLogin()
                .successHandler(userDetailAuthenticationSuccessHandler)
                .failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .logout();

    }
}
