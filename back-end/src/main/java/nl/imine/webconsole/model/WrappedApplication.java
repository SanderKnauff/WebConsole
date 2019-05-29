package nl.imine.webconsole.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WrappedApplication {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String debugString;
    private String commandString;
    private String workingDirectory;
    private String applicationName;
    private int colorHue;
    private String icon;
    private String iconContentType;

    public WrappedApplication(String id, String debugString, String commandString, String workingDirectory) {
        this.id = id;
        this.debugString = debugString;
        this.commandString = commandString;
        this.workingDirectory = workingDirectory;
    }

    public String getId() {
        return id;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getColorHue() {
        return colorHue;
    }

    public void setColorHue(int colorHue) {
        this.colorHue = colorHue;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }
}
