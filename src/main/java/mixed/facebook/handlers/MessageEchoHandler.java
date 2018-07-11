package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.webhook.event.MessageEchoEvent;

import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageEchoHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageEchoHandler.class);
    private final UserDetailsSender textSender;


    @Autowired
    public MessageEchoHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
    }

    public void handleMessageEchoEvent(MessageEchoEvent event) {
        logger.debug("Handling MessageEchoEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final String recipientId = event.recipientId();
        logger.debug("recipientId: {}", recipientId);
        final String messageId = event.messageId();
        logger.debug("messageId: {}", messageId);
        final Instant timestamp = event.timestamp();
        logger.debug("timestamp: {}", timestamp);

        logger.info("Received echo for message '{}' that has been sent to recipient '{}' by sender '{}' at '{}'", messageId, recipientId, senderId, timestamp);
    }
}
