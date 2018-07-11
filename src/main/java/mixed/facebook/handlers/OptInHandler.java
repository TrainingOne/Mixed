package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.webhook.event.OptInEvent;
import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OptInHandler {


    private static final Logger logger = LoggerFactory.getLogger(OptInHandler.class);
    private final UserDetailsSender textSender;

    @Autowired
    public OptInHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
    }

    public void handleOptInEvent(OptInEvent event) {
        logger.debug("Handling OptInEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final String recipientId = event.recipientId();
        logger.debug("recipientId: {}", recipientId);
        final String passThroughParam = event.refPayload().orElse("empty payload");
        logger.debug("passThroughParam: {}", passThroughParam);
        final Instant timestamp = event.timestamp();
        logger.debug("timestamp: {}", timestamp);

        logger.info("Received authentication for user '{}' and page '{}' with pass through param '{}' at '{}'", senderId, recipientId, passThroughParam,
                timestamp);
        textSender.sendText(senderId, "Authentication successful");
    }
}
