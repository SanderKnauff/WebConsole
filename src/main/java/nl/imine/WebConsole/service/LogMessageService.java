package nl.imine.WebConsole.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.imine.WebConsole.model.LogLine;
import nl.imine.WebConsole.util.LimitedSizeQueue;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Service
public class LogMessageService implements ApplicationListener<SessionSubscribeEvent> {

    private final SimpMessagingTemplate template;
    private final List<LogLine> historyBuffer = new LimitedSizeQueue<>(400);
    private final List<LogLine> sendBuffer = Collections.synchronizedList(new ArrayList<>());

    public LogMessageService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendToLogSocket(LogLine logLine) {
        sendBuffer.add(logLine);
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        template.convertAndSend("/logs", historyBuffer);
    }

    @Scheduled(fixedDelayString = "${websocket.interval}")
    public void updateWebSockets() {
        if(!sendBuffer.isEmpty()) {
            historyBuffer.addAll(sendBuffer);
            template.convertAndSend("/logs", sendBuffer);
            sendBuffer.clear();
        }
    }
}
