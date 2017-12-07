package nl.imine.WebConsole.model;

public class WrappedApplicationOptions {

    private String applicationName;
    private int colorHue;
    private String icon;
    private String iconContentType;

    public WrappedApplicationOptions() {
    }

    public WrappedApplicationOptions(String applicationName, int colorHue, String icon, String iconContentType) {
        this.applicationName = applicationName;
        this.colorHue = colorHue;
        this.icon = icon;
        this.iconContentType = iconContentType;
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
