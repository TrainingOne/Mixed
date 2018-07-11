package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.webhook.event.QuickReplyMessageEvent;
import mixed.facebook.functionality.MessageHolder;
import mixed.facebook.senders.mediamessage.MediaMessageSender;
import mixed.facebook.senders.templatemessage.GenericMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QuickReplyHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuickReplyHandler.class);
    private final GenericMessageSender genericMessageSender;
    private final MediaMessageSender mediaMessageSender;
    public static final String MUSIC_ACTION = "DEVELOPER_DEFINED_PAYLOAD_FOR_MUSIC_PARSING";

    @Autowired
    public QuickReplyHandler(final Messenger messenger) {

        this.mediaMessageSender = new MediaMessageSender(messenger);
        this.genericMessageSender = new GenericMessageSender(messenger);
    }


    public void handleQuickReplyMessageEvent(QuickReplyMessageEvent event) throws IOException, MessengerIOException {
        logger.debug("Handling QuickReplyMessageEvent");
        final String payload = event.payload();
        logger.debug("payload: {}", payload);
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final String messageId = event.messageId();
        logger.debug("messageId: {}", messageId);
        logger.info("Received quick reply for message '{}' with payload '{}'", messageId, payload);
        try {
            if (event.payload().equals(MUSIC_ACTION))
                genericMessageSender.sendGenericMusicParserMessage(senderId, MessageHolder.getInstance().getLastMessage(senderId));
            else
                mediaMessageSender.sendPictureParseResult(senderId, MessageHolder.getInstance().getLastMessage(senderId));
        } catch (MessengerApiException e) {
            handleSendException(e);
        }
    }

    private void handleSendException(MessengerApiException e) {
    }
}

