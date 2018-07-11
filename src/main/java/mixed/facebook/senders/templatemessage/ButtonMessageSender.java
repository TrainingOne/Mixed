package mixed.facebook.senders.templatemessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.template.ButtonTemplate;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.CallButton;
import com.github.messenger4j.send.message.template.button.UrlButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;


public class ButtonMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(ButtonMessageSender.class);
    private Messenger messenger;

    public ButtonMessageSender(final Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendButtonMessage(String recipientId) throws MessengerApiException, MessengerIOException, MalformedURLException {
        final List<Button> buttons = Arrays.asList(
                UrlButton.create("Open our website", new URL("https://google.com"), of(WebviewHeightRatio.TALL), of(false), empty(), empty()),
                UrlButton.create("TOP 100 Music", new URL("https://www.musicoutfitters.com/top-100-songs.htm")), CallButton.create("\uD83D\uDCDE Call Representative", "+15105551234"));

        final ButtonTemplate buttonTemplate = ButtonTemplate.create("How can we help you", buttons);
        final TemplateMessage templateMessage = TemplateMessage.create(buttonTemplate);
        final MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, templateMessage);
        this.messenger.send(messagePayload);
    }
}
