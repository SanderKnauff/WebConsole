package nl.imine.webconsole.service;

import nl.imine.webconsole.controller.LogMessageController;
import nl.imine.webconsole.model.ApplicationProcess;
import nl.imine.webconsole.model.LogLine;
import nl.imine.webconsole.model.LogType;
import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.thread.ApplicationAliveMonitor;
import nl.imine.webconsole.thread.ApplicationStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationProcessService implements ApplicationAliveMonitor.ApplicationExitListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProcessService.class);

    private final LogMessageController logMessageController;
    private final WrappedApplicationService wrappedApplicationService;
    private final List<ApplicationProcess> currentProcesses;

    public ApplicationProcessService(LogMessageController logMessageController, WrappedApplicationService wrappedApplicationService) {
        this.logMessageController = logMessageController;
        this.wrappedApplicationService = wrappedApplicationService;
        this.currentProcesses = new ArrayList<>();
    }

    public void startApplication(ApplicationProcess applicationProcess, boolean debug) {
        if (!isApplicationAlive(applicationProcess)) {
            if (debug) {
                logMessageController.sendToLogSocket(applicationProcess.getWrappedApplication(), new LogLine("Starting application in debug mode", LogType.ERR));
            } else {
                logMessageController.sendToLogSocket(applicationProcess.getWrappedApplication(), new LogLine("Starting application", LogType.SYS));
            }
            applicationProcess.setShouldRestart(true);
            String startCommand = debug ? applicationProcess.getWrappedApplication().getDebugString() : applicationProcess.getWrappedApplication().getCommandString();
            ProcessBuilder processBuilder = new ProcessBuilder(startCommand.split("\\s"));
            processBuilder.directory(new File(applicationProcess.getWrappedApplication().getWorkingDirectory()));
            try {
                processBuilder.redirectErrorStream(true);
                applicationProcess.setProcess(processBuilder.start());
                applicationProcess.getProcess().ifPresent(process -> {
                    ApplicationAliveMonitor applicationAliveMonitor = new ApplicationAliveMonitor(applicationProcess, this);
                    applicationAliveMonitor.start();
                    applicationProcess.setApplicationStreamHandler(new ApplicationStreamHandler(logMessageController, applicationProcess.getWrappedApplication(), process.getInputStream(), process.getErrorStream(), process.getOutputStream()));
                    applicationProcess.getApplicationStreamHandler().ifPresent(ApplicationStreamHandler::start);
                });
            } catch (IOException e) {
                logger.error("Exception while starting the application ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    public void stopApplication(ApplicationProcess applicationProcess, boolean restart) {
        applicationProcess.setShouldRestart(restart);
        if (isApplicationAlive(applicationProcess)) {
            applicationProcess.getApplicationStreamHandler().ifPresent(applicationStreamHandler -> {
                try {
                    applicationStreamHandler.getStdIn().write("stop\n");
                    applicationStreamHandler.getStdIn().flush();
                } catch (IOException e) {
                    logger.error("Exception while stopping the application ({}: {})", e.getClass().getSimpleName(), e.getMessage());
                }
            });
        }
    }

    public void sendCommandToApplication(ApplicationProcess applicationProcess, String command) {
        if (isApplicationAlive(applicationProcess)) {
            applicationProcess.getApplicationStreamHandler().ifPresent(applicationStreamHandler -> {
                try {
                    applicationStreamHandler.getStdIn().write(command + "\n");
                    applicationStreamHandler.getStdIn().flush();
                } catch (IOException e) {
                    logger.error("Exception while sending command ({}) to application ({}: {})", command, e.getClass().getSimpleName(), e.getMessage());
                }
            });
        }
    }

    @Override
    public void onProcessClose(ApplicationProcess applicationProcess) {
        logMessageController.sendToLogSocket(applicationProcess.getWrappedApplication(), new LogLine("Application has terminated", LogType.SYS));
        applicationProcess.getApplicationStreamHandler().ifPresent(ApplicationStreamHandler::close);
        if (applicationProcess.isShouldRestart()) {
            startApplication(applicationProcess, false);
        }
    }

    private boolean isApplicationAlive(ApplicationProcess applicationProcess) {
        return applicationProcess.getProcess().map(Process::isAlive).orElse(Boolean.FALSE);
    }

    public ApplicationProcess getOrCreateApplicationProcess(WrappedApplication application) {
        Optional<ApplicationProcess> process = getApplicationProcess(application.getId());
        if(process.isPresent()) {
            return process.get();
        } else {
            ApplicationProcess applicationProcess = new ApplicationProcess(application);
            currentProcesses.add(applicationProcess);
            return applicationProcess;
        }
    }

    public Optional<ApplicationProcess> getApplicationProcess(String id) {
        return currentProcesses.stream()
                .filter(applicationProcess -> applicationProcess.getWrappedApplication().getId().equalsIgnoreCase(id))
                .findFirst();
    }
}
