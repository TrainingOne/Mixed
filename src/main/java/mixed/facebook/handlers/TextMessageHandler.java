package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import lombok.extern.slf4j.Slf4j;
import mixed.facebook.functionality.MessageHolder;
import mixed.facebook.senders.mediamessage.MediaMessageSender;
import mixed.facebook.senders.templatemessage.*;
import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;


@Slf4j
@Service
public class TextMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);
    private final UserDetailsSender textSender;
    private final MediaMessageSender mediaSender;
    private final GenericListMessageSender genericListMessageSender;
    private final GenericMessageSender genericMessageSender;
    private final ButtonMessageSender buttonMessageSender;
    private final ReceiptMessageSender receiptMessageSender;
    private final QuickReplaySender quickReplaySender;

    @Autowired
    public TextMessageHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
        this.mediaSender = new MediaMessageSender(messenger);
        this.genericListMessageSender = new GenericListMessageSender(messenger);
        this.genericMessageSender = new GenericMessageSender(messenger);
        this.buttonMessageSender = new ButtonMessageSender(messenger);
        this.receiptMessageSender = new ReceiptMessageSender(messenger);
        this.quickReplaySender = new QuickReplaySender(messenger);
    }

    public void handleTextMessageEvent(TextMessageEvent event) {
        logger.debug("Received TextMessageEvent: {}", event);
        final String messageId = event.messageId();
        final String messageText = event.text();
        final String senderId = event.senderId();
        final Instant timestamp = event.timestamp();
        logger.info("Received message '{}' with text '{}' from user '{}' at '{}'", messageId, messageText, senderId, timestamp);

        try {
            MessageHolder.getInstance().addToMessageArchive(senderId, messageText);
            switch (messageText.toLowerCase()) {
                case "hi":
                case "hello":
                    textSender.sendUserDetails(senderId);
                    break;
                case "image":
                case "picture":
                    mediaSender.sendMedia(senderId, RichMediaAsset.Type.IMAGE, "https://image.freepik.com/free-photo/cute-cat-picture_1122-449.jpg");
                    break;
                case "audio":
                    mediaSender.sendMedia(senderId, RichMediaAsset.Type.AUDIO, "http://k006.kiwi6.com/hotlink/89egzfld8n/Fragile.mp3");
                    break;
                case "video":
                    mediaSender.sendMedia(senderId, RichMediaAsset.Type.VIDEO, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
                    break;
                case "support":
                case "help":
                    buttonMessageSender.sendButtonMessage(senderId);
                    break;
                case "receipt":
                    receiptMessageSender.sendReceiptMessage(senderId);
                    break;
                case "bb":
                case "bye":
                    textSender.sendText(senderId,  "Good Luck! Have a nice day!");
                    break;
                default:
                    quickReplaySender.sendQuickReply(senderId);


            }
        } catch (MessengerApiException | MessengerIOException | MalformedURLException e) {
            handleSendException(e);
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

    private void handleSendException(Exception e) {
    }
}
