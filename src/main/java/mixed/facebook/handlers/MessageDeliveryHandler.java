package mixed.facebook.handlers;

import com.github.messenger4j.webhook.event.MessageDeliveredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class MessageDeliveryHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageDeliveryHandler.class);

    @Autowired
    public MessageDeliveryHandler() {
    }

    public void handleMessageDeliveredEvent(MessageDeliveredEvent event) {
        logger.debug("Handling MessageDeliveredEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final List<String> messageIds = event.messageIds().orElse(Collections.emptyList());
        final Instant watermark = event.watermark();
        logger.debug("watermark: {}", watermark);

        messageIds.forEach(messageId -> {
            logger.info("Received delivery confirmation for message '{}'", messageId);
        });

        logger.info("All messages before '{}' were delivered to user '{}'", watermark, senderId);
    }

}
