package nl.imine.webconsole.thread;

import nl.imine.webconsole.model.ApplicationProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationAliveMonitor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExitListener.class);

    private final ApplicationProcess applicationProcess;
    private final ApplicationExitListener applicationExitListener;

    public ApplicationAliveMonitor(ApplicationProcess applicationProcess, ApplicationExitListener applicationExitListener) {
        this.applicationProcess = applicationProcess;
        this.applicationExitListener = applicationExitListener;
    }

    @Override
    public void run() {
        applicationProcess.getProcess().ifPresent(process -> {
            try {
                process.waitFor();
                applicationExitListener.onProcessClose(applicationProcess);
            } catch (InterruptedException e) {
                logger.error("Exception while listening for process to finish ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        });
    }

    public interface ApplicationExitListener {
        void onProcessClose(ApplicationProcess applicationProcess);
    }
}
