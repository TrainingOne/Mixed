package mixed.facebook.senders.templatemessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.message.quickreply.QuickReply;
import com.github.messenger4j.send.message.quickreply.TextQuickReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class QuickReplaySender {
    private static final Logger logger = LoggerFactory.getLogger(QuickReplaySender.class);
    private Messenger messenger;
    public static final String MUSIC_ACTION = "DEVELOPER_DEFINED_PAYLOAD_FOR_MUSIC_PARSING";
    public static final String PICTURE_ACTION = "DEVELOPER_DEFINED_PAYLOAD_FOR_PICTURE_PARSING";
    public QuickReplaySender(final Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendQuickReply(String recipientId) throws MessengerApiException, MessengerIOException {

        final TextQuickReply quickReplyMusic = TextQuickReply.create("Music", MUSIC_ACTION);
        final TextQuickReply quickReplyPictures = TextQuickReply.create("Picture", PICTURE_ACTION);
        final List<QuickReply> quickReplies = Arrays.asList(quickReplyMusic, quickReplyPictures);

        final TextMessage message = TextMessage.create("What are you looking for?", of(quickReplies), empty());
        final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE, message);
        messenger.send(MessagePayload.create(recipientId, MessagingType.RESPONSE, message));
    }
}
