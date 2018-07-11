package mixed.facebook.handlers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.webhook.event.AccountLinkingEvent;
import mixed.facebook.senders.textmessage.UserDetailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountLinkingHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccountLinkingHandler.class);
    private final UserDetailsSender textSender;

    @Autowired
    public AccountLinkingHandler(final Messenger messenger) {
        this.textSender = new UserDetailsSender(messenger);
    }


    public void handleAccountLinkingEvent(AccountLinkingEvent event) {
        logger.debug("Handling AccountLinkingEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);
        final AccountLinkingEvent.Status accountLinkingStatus = event.status();
        logger.debug("accountLinkingStatus: {}", accountLinkingStatus);
        final String authorizationCode = event.authorizationCode().orElse("Empty authorization code!!!");
        logger.debug("authorizationCode: {}", authorizationCode);
        logger.info("Received account linking event for user '{}' with status '{}' and auth code '{}'", senderId, accountLinkingStatus, authorizationCode);
        textSender.sendText(senderId, "Wow your know how to send links \uD83D\uDE2F, how about search for lyric \uD83D\uDE09");
    }
}
