package nl.imine.webconsole.model;

import nl.imine.webconsole.thread.ApplicationStreamHandler;

import java.util.Optional;

public class ApplicationProcess {

    private final WrappedApplication wrappedApplication;
    private ApplicationStreamHandler applicationStreamHandler;
    private boolean shouldRestart = true;
    private Process process;

    public ApplicationProcess(WrappedApplication wrappedApplication) {
        this.wrappedApplication = wrappedApplication;
    }

    public WrappedApplication getWrappedApplication() {
        return wrappedApplication;
    }

    public Optional<ApplicationStreamHandler> getApplicationStreamHandler() {
        return Optional.ofNullable(applicationStreamHandler);
    }

    public void setApplicationStreamHandler(ApplicationStreamHandler applicationStreamHandler) {
        this.applicationStreamHandler = applicationStreamHandler;
    }

    public boolean isShouldRestart() {
        return shouldRestart;
    }

    public void setShouldRestart(boolean shouldRestart) {
        this.shouldRestart = shouldRestart;
    }

    public Optional<Process> getProcess() {
        return Optional.ofNullable(process);
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
