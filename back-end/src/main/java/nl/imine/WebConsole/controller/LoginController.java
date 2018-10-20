package nl.imine.WebConsole.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }
}
