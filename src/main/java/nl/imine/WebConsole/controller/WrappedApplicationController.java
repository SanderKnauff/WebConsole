package nl.imine.WebConsole.controller;

import nl.imine.WebConsole.component.WrappedJarApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class WrappedApplicationController {

    private final WrappedJarApplication wrappedJarApplication;

    public WrappedApplicationController(WrappedJarApplication wrappedJarApplication) {
        this.wrappedJarApplication = wrappedJarApplication;
    }

    @GetMapping("/start")
    public void startApplication() {
        wrappedJarApplication.startApplication();
    }

    @GetMapping("/stop")
    public void stopApplication() {
        wrappedJarApplication.stopApplication(false);
    }

    @GetMapping("/restart")
    public void restartApplication() {
        wrappedJarApplication.stopApplication(true);
    }

    @PostMapping("/sendCommand")
    public void startApplication(@RequestBody String command) {
        if(command.toLowerCase().startsWith("stop")) {
            wrappedJarApplication.stopApplication(false);
        } else {
            wrappedJarApplication.sendCommandToApplication(command);
        }
    }

}
