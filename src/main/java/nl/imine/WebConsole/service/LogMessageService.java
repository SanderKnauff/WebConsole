package nl.imine.WebConsole.service;

import nl.imine.WebConsole.model.LogLine;
import nl.imine.WebConsole.util.LimitedSizeQueue;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Service
public class LogMessageService implements ApplicationListener<SessionSubscribeEvent> {

    private final SimpMessagingTemplate template;
    private final LimitedSizeQueue<LogLine> historyBuffer = new LimitedSizeQueue<>(400);

    public LogMessageService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendToLogSocket(LogLine logLine) {
        historyBuffer.add(logLine);
        template.convertAndSend("/logs", logLine);
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        template.convertAndSend("/logs", historyBuffer);
    }
}
