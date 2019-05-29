package nl.imine.webconsole.model;

public class LogLine {

    private final String line;
    private final LogType logType;

    public LogLine(String line, LogType logType) {
        this.line = line;
        this.logType = logType;
    }

    public String getLine() {
        return line;
    }

    public LogType getLogType() {
        return logType;
    }
}
