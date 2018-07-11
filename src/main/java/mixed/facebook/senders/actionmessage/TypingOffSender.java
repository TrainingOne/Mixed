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
public class TypingOffSender {
    private static final Logger logger = LoggerFactory.getLogger(TypingOffSender.class);
    private Messenger messenger;

    public TypingOffSender(final Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendTypingOff(String recipientId) throws MessengerApiException, MessengerIOException {
        this.messenger.send(SenderActionPayload.create(recipientId, SenderAction.TYPING_OFF));
    }

}
