package mixed.facebook.senders.templatemessage;
import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.template.ListTemplate;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.UrlButton;
import com.github.messenger4j.send.message.template.common.Element;
import mixed.parsers.LyricParserService;
import mixed.parsers.LyricPlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class GenericListMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(GenericListMessageSender.class);
    private Messenger messenger;
    LyricParserService parser = new LyricParserService();
    @Autowired
    public GenericListMessageSender(final Messenger messenger) {this.messenger = messenger;}

    public void sendListMessageMessage(String recipientId, String message) throws MessengerApiException, MessengerIOException, IOException {

        parser.getSongsLyricsUrlsByKey(message);
        List<LyricPlain> songs = parser.getLyrics();

        List<Button> riftButtons = new ArrayList<>();
        riftButtons.add(UrlButton.create("Open Lyrics", new URL(songs.get(0).getLink())));

        final List<Element> elements = new ArrayList<>();
        elements.add(Element.create(songs.get(0).getName(), of("Song text"), of(new URL("http://pluspng.com/img-png/music-notes-png-400.png")), Optional.empty(), of(riftButtons)));

        final ListTemplate listTemplate = ListTemplate.create(elements);
        final TemplateMessage templateMessage = TemplateMessage.create(listTemplate);
        final MessagePayload messagePayload = MessagePayload.create(recipientId, MessagingType.RESPONSE, templateMessage);
        this.messenger.send(messagePayload);
    }
}

