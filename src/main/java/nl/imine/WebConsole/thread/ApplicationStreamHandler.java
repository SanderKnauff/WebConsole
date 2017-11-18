package nl.imine.WebConsole.thread;

import nl.imine.WebConsole.model.LogLine;
import nl.imine.WebConsole.model.LogType;
import nl.imine.WebConsole.service.LogMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ApplicationStreamHandler extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStreamHandler.class);

    private final LogMessageService logMessageService;
    private final BufferedReader stdOut;
    private final BufferedReader stdErr;
    private final BufferedWriter stdIn;

    private boolean running = true;

    public ApplicationStreamHandler(LogMessageService logMessageService, InputStream stdOut, InputStream stdErr, OutputStream stdIn) {
        this.logMessageService = logMessageService;
        this.stdOut = new BufferedReader(new InputStreamReader(stdOut));
        this.stdErr = new BufferedReader(new InputStreamReader(stdErr));
        this.stdIn = new BufferedWriter(new OutputStreamWriter(stdIn));
    }

    @Override
    public void run() {
        try {
            while (running) {
                String stdOutString = stdOut.readLine();
                String stdErrString = stdErr.readLine();

                if (stdOutString != null) {
                    logMessageService.sendToLogSocket(new LogLine(stdOutString, LogType.OUT));
                }

                if (stdErrString != null) {
                    logMessageService.sendToLogSocket(new LogLine(stdErrString, LogType.ERR));
                }
            }
        } catch (IOException e) {
            logger.error("Exception while reading stream ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public BufferedWriter getStdIn() {
        return stdIn;
    }

    public void close() {
        this.running = false;
        try {
            stdOut.close();
            stdErr.close();
            stdIn.close();
        } catch (IOException e) {
            logger.error("Exception while closing stream ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
