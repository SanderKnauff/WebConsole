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
    public void startApplication(@RequestParam(name="debug", defaultValue = "false", required = false) boolean debug) {
        wrappedJarApplication.startApplication(debug);
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
