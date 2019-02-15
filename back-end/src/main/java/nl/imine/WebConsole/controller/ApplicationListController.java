package nl.imine.WebConsole.controller;

import nl.imine.WebConsole.dto.ApplicationDetailsDto;
import nl.imine.WebConsole.service.WrappedApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/application-list")
public class ApplicationListController {

    private final WrappedApplicationService wrappedApplicationService;

    public ApplicationListController(WrappedApplicationService wrappedApplicationService) {
        this.wrappedApplicationService = wrappedApplicationService;
    }

    @GetMapping
    public List<ApplicationDetailsDto> getApplicationList() {
        return wrappedApplicationService.getAll().stream()
                .map(wrappedApplication ->
                        new ApplicationDetailsDto(
                                wrappedApplication.getId(),
                                wrappedApplication.getApplicationName(),
                                wrappedApplication.getColorHue()
                        )
                )
                .collect(Collectors.toList());
    }
}
