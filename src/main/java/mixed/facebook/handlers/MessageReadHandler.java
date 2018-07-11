package mixed.facebook.handlers;

import com.github.messenger4j.webhook.event.MessageReadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageReadHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageReadHandler.class);

    @Autowired
    public MessageReadHandler() {
    }

    public void handleMessageReadEvent(MessageReadEvent event) {
        logger.debug("Handling MessageReadEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final Instant watermark = event.watermark();
        logger.debug("watermark: {}", watermark);

        logger.info("All messages before '{}' were read by user '{}'", watermark, senderId);
    }
}
