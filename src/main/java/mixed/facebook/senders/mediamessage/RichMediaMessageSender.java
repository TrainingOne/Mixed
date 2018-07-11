package mixed.facebook.senders.mediamessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.RichMediaMessage;
import com.github.messenger4j.send.message.richmedia.UrlRichMediaAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RichMediaMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(RichMediaMessageSender.class);
    private Messenger messenger;

    public RichMediaMessageSender(Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendMedia(String recipientId, UrlRichMediaAsset richMediaAsset) throws MessengerApiException, MessengerIOException {
        final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
        final MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, richMediaMessage);
        this.messenger.send(messagePayload);
    }
}
