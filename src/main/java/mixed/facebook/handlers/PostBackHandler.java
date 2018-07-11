package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.webhook.event.PostbackEvent;
import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostBackHandler {

    private static final Logger logger = LoggerFactory.getLogger(PostBackHandler.class);
    private final UserDetailsSender textSender;


    @Autowired
    public PostBackHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
    }

    public void handlePostbackEvent(PostbackEvent event) {
        logger.debug("Handling PostbackEvent");
        final String payload = event.payload().orElse("empty payload");
        logger.debug("payload: {}", payload);
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final Instant timestamp = event.timestamp();
        logger.debug("timestamp: {}", timestamp);
        logger.info("Received postback for user '{}' and page '{}' with payload '{}' at '{}'", senderId, senderId, payload, timestamp);
        textSender.sendText(senderId, "Postback event tapped");
    }

}
