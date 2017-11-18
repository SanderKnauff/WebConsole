package nl.imine.WebConsole.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationAliveMonitor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExitListener.class);

    private final Process process;
    private final ApplicationExitListener applicationExitListener;

    public ApplicationAliveMonitor(Process process, ApplicationExitListener applicationExitListener) {
        this.process = process;
        this.applicationExitListener = applicationExitListener;
    }

    @Override
    public void run() {
        try {
            process.waitFor();
            applicationExitListener.onApplicationExit();
        } catch (InterruptedException e) {
            logger.error("Exception while listening for process to finish ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public interface ApplicationExitListener {
        void onApplicationExit();
    }
}
