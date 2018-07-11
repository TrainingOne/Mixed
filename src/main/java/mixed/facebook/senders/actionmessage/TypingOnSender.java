package mixed.facebook.senders.actionmessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.SenderActionPayload;
import com.github.messenger4j.send.senderaction.SenderAction;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TypingOnSender {
    private static final Logger logger = LoggerFactory.getLogger(TypingOnSender.class);
    private Messenger messenger;

    public TypingOnSender(final Messenger messenger) {
        this.messenger = messenger;
    }

    private void sendTypingOn(String recipientId) throws MessengerApiException, MessengerIOException {
        this.messenger.send(SenderActionPayload.create(recipientId, SenderAction.TYPING_ON));
    }
}
