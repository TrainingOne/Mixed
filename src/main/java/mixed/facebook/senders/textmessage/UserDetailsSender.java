package mixed.facebook.senders.textmessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.userprofile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDetailsSender extends TextMessageSender{

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsSender.class);
    private final Messenger messenger;


    public UserDetailsSender(final Messenger messenger) {
        super(messenger);
        this.messenger = messenger;
    }

    @Override
    public void sendText(String recipientId, String text) {
        super.sendText(recipientId, text);
    }

    public void sendUserDetails(String recipientId) throws MessengerIOException, MessengerApiException {
        final UserProfile userProfile = this.messenger.queryUserProfile(recipientId);
        super.sendText(recipientId, String.format("Hello %s nice to meet you!"+"\n"+"\n"+"I can help you to find a pictures or song lyric" + "\n"+"\n"+"Just type name of your song (eminem .. etc)"+"\n"+"Or describe picture you are looking for"+"\n"+"\n"+"Type [help] or [support] if you have any problems", userProfile.firstName(), userProfile.gender(), userProfile.profilePicture()));
        logger.info("User Profile Picture: {}", userProfile.profilePicture());
    }
}
