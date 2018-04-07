package nl.imine.webconsole.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.model.WrappedApplicationOptions;
import nl.imine.webconsole.service.WrappedApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ApplicationListController {

    private final WrappedApplicationService wrappedApplicationService;
    private final ObjectMapper objectMapper;

    public ApplicationListController(WrappedApplicationService wrappedApplicationService, ObjectMapper objectMapper) {
        this.wrappedApplicationService = wrappedApplicationService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ModelAndView getApplicationListPage() throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("list");
        Map<String, WrappedApplicationOptions> options = wrappedApplicationService.getAllWrappedApplications().stream()
                .collect(Collectors.toMap(WrappedApplication::getId, WrappedApplication::getWrappedApplicationOptions));
        modelAndView.addObject("applications", objectMapper.writeValueAsString(options));
        return modelAndView;
    }
}
