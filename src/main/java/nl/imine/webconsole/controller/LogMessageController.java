package nl.imine.webconsole.controller;

import nl.imine.webconsole.model.LogLine;
import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.util.LimitedSizeQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class LogMessageController {

    private static final Logger logger = LoggerFactory.getLogger(LogMessageController.class);

    private final SimpMessagingTemplate template;
    private final Map<String, List<LogLine>> historyBuffers = new HashMap<>();
    private final Map<String, List<LogLine>> sendBuffers = new HashMap<>();

    public LogMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendToLogSocket(WrappedApplication wrappedApplication, LogLine logLine) {
        if (!sendBuffers.containsKey(wrappedApplication.getId())) {
            sendBuffers.put(wrappedApplication.getId(), Collections.synchronizedList(new ArrayList<>()));
        }
        sendBuffers.get(wrappedApplication.getId()).add(logLine);
    }

    @SubscribeMapping("/logs/{serverId}")
    public void onApplicationSubscribe(@DestinationVariable String serverId) {
        logger.info("Client subscribed to /{}", serverId);
        if (historyBuffers.containsKey(serverId)) {
            template.convertAndSend("/logs/" + serverId, historyBuffers.get(serverId)); //TODO Send History buffers. Find out which channel was subscribed to
        }
    }

    @Scheduled(fixedDelayString = "${websocket.interval}")
    public void updateWebSockets() {
        for (String key : sendBuffers.keySet()) {
            if (!historyBuffers.containsKey(key)) {
                historyBuffers.put(key, new LimitedSizeQueue<>(400));
            }
            List<LogLine> sendBuffer = sendBuffers.get(key);
            List<LogLine> historyBuffer = historyBuffers.get(key);
            if (!sendBuffer.isEmpty()) {
                historyBuffer.addAll(sendBuffer);
                template.convertAndSend("/logs/" + key, sendBuffer);
                sendBuffer.clear();
            }
        }
    }
}
