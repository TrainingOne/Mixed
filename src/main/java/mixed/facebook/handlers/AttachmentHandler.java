package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.attachment.Attachment;
import com.github.messenger4j.webhook.event.attachment.LocationAttachment;
import com.github.messenger4j.webhook.event.attachment.RichMediaAttachment;
import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class AttachmentHandler {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentHandler.class);
    private final UserDetailsSender textSender;


    @Autowired
    public AttachmentHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
    }

    public void handleAttachmentMessageEvent(AttachmentMessageEvent event) {
        logger.debug("Handling QuickReplyMessageEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        for (Attachment attachment : event.attachments()) {
            if (attachment.isRichMediaAttachment()) {
                final RichMediaAttachment richMediaAttachment = attachment.asRichMediaAttachment();
                final RichMediaAttachment.Type type = richMediaAttachment.type();
                final URL url = richMediaAttachment.url();
                logger.debug("Received rich media attachment of type '{}' with url: {}", type, url);
                final String text = "Amazing piece of art";
                textSender.sendText(senderId, text);
            } else if (attachment.isLocationAttachment()) {
                final LocationAttachment locationAttachment = attachment.asLocationAttachment();
                final double longitude = locationAttachment.longitude();
                final double latitude = locationAttachment.latitude();
                logger.debug("Received location information (long: {}, lat: {})", longitude, latitude);
                final String text = String.format("Location received (long: %s, lat: %s)", String.valueOf(longitude), String.valueOf(latitude));
                textSender.sendText(senderId, text);
            }
        }
    }
}

