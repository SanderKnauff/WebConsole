package nl.imine.webconsole.model;

public class WrappedApplication {

    private String id;
    private String debugString;
    private String commandString;
    private String workingDirectory;
    private WrappedApplicationOptions wrappedApplicationOptions;

    public WrappedApplication() {
    }

    public WrappedApplication(String id, String debugString, String commandString, String workingDirectory, WrappedApplicationOptions wrappedApplicationOptions) {
        this.id = id;
        this.debugString = debugString;
        this.commandString = commandString;
        this.workingDirectory = workingDirectory;
        this.wrappedApplicationOptions = wrappedApplicationOptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDebugString() {
        return debugString;
    }

    public void setDebugString(String debugString) {
        this.debugString = debugString;
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public WrappedApplicationOptions getWrappedApplicationOptions() {
        return wrappedApplicationOptions;
    }

    public void setWrappedApplicationOptions(WrappedApplicationOptions wrappedApplicationOptions) {
        this.wrappedApplicationOptions = wrappedApplicationOptions;
    }
}
