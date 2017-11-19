package nl.imine.WebConsole.component;

import nl.imine.WebConsole.model.LogLine;
import nl.imine.WebConsole.model.LogType;
import nl.imine.WebConsole.service.LogMessageService;
import nl.imine.WebConsole.thread.ApplicationAliveMonitor;
import nl.imine.WebConsole.thread.ApplicationStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class WrappedJarApplication implements ApplicationAliveMonitor.ApplicationExitListener {

    private static final Logger logger = LoggerFactory.getLogger(WrappedJarApplication.class);

    private final LogMessageService logMessageService;
    private final String commandString;
    private final String debugCommandString;
    private final String workingDir;

    private Process process;
    private boolean shouldRestart = true;
    private ApplicationStreamHandler applicationStreamHandler;

    public WrappedJarApplication(LogMessageService logMessageService,
                                 @Value("${application.command}") String commandString,
                                 @Value("${application.command.debug}") String debugCommandString,
                                 @Value("${application.workingdir}") String workingDir) {
        this.logMessageService = logMessageService;
        this.commandString = commandString;
        this.debugCommandString = debugCommandString;
        this.workingDir = workingDir;
    }

    @PostConstruct
    public void onPostConstruct() {
        startApplication(false);
    }

    public void startApplication(boolean debug) {
        if(!isServerActive()) {
            if(debug) {
                logMessageService.sendToLogSocket(new LogLine("Starting application in debug mode", LogType.ERR));
            } else {
                logMessageService.sendToLogSocket(new LogLine("Starting application", LogType.SYS));
            }
            shouldRestart = true;
            String startCommand = debug ? debugCommandString : commandString;
            ProcessBuilder processBuilder = new ProcessBuilder(startCommand.split("\\s"));
            processBuilder.directory(new File(workingDir));
            try {
                processBuilder.redirectErrorStream(true);
                process = processBuilder.start();
                ApplicationAliveMonitor applicationAliveMonitor = new ApplicationAliveMonitor(process, this);
                applicationAliveMonitor.start();
                applicationStreamHandler = new ApplicationStreamHandler(logMessageService, process.getInputStream(), process.getErrorStream(), process.getOutputStream());
                applicationStreamHandler.start();
            } catch (IOException e) {
                logger.error("Exception while starting the application ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    public void stopApplication(boolean restart) {
        shouldRestart = restart;
        if(isServerActive()) {
            try {
                applicationStreamHandler.getStdIn().write("stop\n");
                applicationStreamHandler.getStdIn().flush();
            } catch (IOException e) {
                logger.error("Exception while stopping the application ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    public void sendCommandToApplication(String command) {
        if(isServerActive()) {
            try {
                applicationStreamHandler.getStdIn().write(command + "\n");
                applicationStreamHandler.getStdIn().flush();
            } catch (IOException e) {
                logger.error("Exception while sending command ({}) to application ({}: {})", command, e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    @Override
    public void onApplicationExit() {
        logMessageService.sendToLogSocket(new LogLine("Application has terminated", LogType.SYS));
        applicationStreamHandler.close();
        if(shouldRestart) {
            startApplication(false);
        }
    }

    private boolean isServerActive(){
        return process != null && process.isAlive();
    }

}
