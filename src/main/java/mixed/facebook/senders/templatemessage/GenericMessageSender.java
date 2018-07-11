package mixed.facebook.senders.templatemessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.template.GenericTemplate;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.UrlButton;
import com.github.messenger4j.send.message.template.common.Element;
import mixed.parsers.LyricParserService;
import mixed.parsers.LyricPlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;


public class GenericMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(GenericMessageSender.class);
    private Messenger messenger;
    LyricParserService parser = new LyricParserService();

    public GenericMessageSender(final Messenger messenger) {
        this.messenger = messenger;
    }

    public void sendGenericMusicParserMessage(String recipientId, String message) throws MessengerApiException, MessengerIOException, IOException {
        parser.getSongsLyricsUrlsByKey(message);
        List<LyricPlain> songs = parser.getLyrics();

        if (!songs.isEmpty()) {

            List<Button> riftButtons = new ArrayList<>();
            riftButtons.add(UrlButton.create("Open song lyrics", new URL(songs.get(0).getLink())));
            final List<Element> elements = new ArrayList<>();
            elements.add(
                    Element.create(songs.get(0).getName(), of("Enjoy your favorite song lyrics"), of(new URL("http://www.grandplaymedia.com/img/work/lyricsfreak-logo.png")), empty(), of(riftButtons)));
            final GenericTemplate genericTemplate = GenericTemplate.create(elements);
            final TemplateMessage templateMessage = TemplateMessage.create(genericTemplate);
            final MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, templateMessage);
            this.messenger.send(messagePayload);

        } else {

            List<Button> riftButtons = new ArrayList<>();
            riftButtons.add(UrlButton.create("Search one more time", new URL("https://google.com")));


            final List<Element> elements = new ArrayList<>();

            elements.add(
                    Element.create("Sorry we didn`t find your song :(", of("You can try again ;)"), of(new URL("https://d33wjekvz3zs1a.cloudfront.net/wp-content/uploads/2016/08/Sorry.jpg")), empty(), empty()));

            final GenericTemplate genericTemplate = GenericTemplate.create(elements);
            final TemplateMessage templateMessage = TemplateMessage.create(genericTemplate);
            final MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, templateMessage);
            this.messenger.send(messagePayload);

        }

    }
}

