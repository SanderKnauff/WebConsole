package nl.imine.webconsole.controller;

import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.service.ApplicationIconService;
import nl.imine.webconsole.service.ApplicationProcessService;
import nl.imine.webconsole.service.WrappedApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/application/{id}")
public class WrappedApplicationController {

    private final ApplicationProcessService applicationProcessService;
    private final WrappedApplicationService wrappedApplicationService;
    private final ApplicationIconService applicationIconService;

    public WrappedApplicationController(ApplicationProcessService applicationProcessService, WrappedApplicationService wrappedApplicationService, ApplicationIconService applicationIconService) {
        this.applicationProcessService = applicationProcessService;
        this.wrappedApplicationService = wrappedApplicationService;
        this.applicationIconService = applicationIconService;
    }

    @ResponseBody
    @GetMapping("/start")
    public void startApplication(@PathVariable String id, @RequestParam(name = "debug", defaultValue = "false", required = false) boolean debug) {
        wrappedApplicationService.findById(id).ifPresent(wrappedApplication -> {
            applicationProcessService.startApplication(applicationProcessService.getOrCreateApplicationProcess(wrappedApplication), debug);
        });
    }

    @ResponseBody
    @GetMapping("/stop")
    public void stopApplication(@PathVariable String id) {
        applicationProcessService.getApplicationProcess(id).ifPresent(applicationProcess -> {
            applicationProcessService.stopApplication(applicationProcess, false);
        });
    }

    @ResponseBody
    @GetMapping("/restart")
    public void restartApplication(@PathVariable String id) {
        applicationProcessService.getApplicationProcess(id).ifPresent(applicationProcess -> {
            applicationProcessService.stopApplication(applicationProcess, true);
        });
    }

    @ResponseBody
    @PostMapping("/sendCommand")
    public void startApplication(@PathVariable String id, @RequestBody String command) {
        applicationProcessService.getApplicationProcess(id).ifPresent(applicationProcess -> {
            applicationProcessService.sendCommandToApplication(applicationProcess, command);
        });
    }

    @ResponseBody
    @GetMapping("/icon")
    public void getIcon(HttpServletResponse response, @PathVariable String id) throws IOException {
        Optional<WrappedApplication> oWrappedApplication = wrappedApplicationService.findById(id);
        if (oWrappedApplication.isPresent()) {
            WrappedApplication wrappedApplication = oWrappedApplication.get();
            Optional<byte[]> oApplicationIcon = applicationIconService.getIcon(wrappedApplication.getIcon());
            if (oApplicationIcon.isPresent()) {
                response.setContentType(wrappedApplication.getIconContentType());
                response.getOutputStream().write(oApplicationIcon.get());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Icon not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrapped application not found");
        }
    }

    @ResponseBody
    @PostMapping(value = "/icon", consumes = {"image/svg+xml", "image/png", "image/jpeg"})
    public void storeIcon(HttpServletResponse response, HttpServletRequest httpServletRequest, @PathVariable String id, @RequestBody byte[] image) throws IOException {
        Optional<WrappedApplication> oWrappedApplication = wrappedApplicationService.findById(id);
        if (oWrappedApplication.isPresent()) {
            WrappedApplication wrappedApplication = oWrappedApplication.get();
            String iconName = "icon_" + wrappedApplication.getId() + "." + httpServletRequest.getHeader("Content-Type").split("/")[1].split("\\+")[0];
            wrappedApplication.setIcon(iconName);
            wrappedApplication.setIconContentType(httpServletRequest.getContentType());
            applicationIconService.saveIcon(iconName, image);
            wrappedApplicationService.save(wrappedApplication);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
