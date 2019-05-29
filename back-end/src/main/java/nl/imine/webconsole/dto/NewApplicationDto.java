package nl.imine.webconsole.dto;

public class NewApplicationDto {

    private final String debugString;
    private final String commandString;
    private final String workingDirectory;
    private final String applicationName;

    public NewApplicationDto(String id, String debugString, String commandString, String workingDirectory, String applicationName) {
        this.debugString = debugString;
        this.commandString = commandString;
        this.workingDirectory = workingDirectory;
        this.applicationName = applicationName;
    }

    public String getDebugString() {
        return debugString;
    }

    public String getCommandString() {
        return commandString;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
