package mixed.facebook.senders.mediamessage;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
import com.github.messenger4j.send.message.richmedia.UrlRichMediaAsset;
import mixed.parsers.PictureParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MediaMessageSender extends RichMediaMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MediaMessageSender.class);
    private PictureParserService pictureParserService;
    public MediaMessageSender(final Messenger messenger) {
        super(messenger);
        pictureParserService = new PictureParserService();
    }

    public void sendMedia(String recipientId, RichMediaAsset.Type type, String url) throws MessengerApiException, MessengerIOException, MalformedURLException {
        final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(type, new URL(url));
        super.sendMedia(recipientId, richMediaAsset);
    }
    public void sendPictureParseResult(String recipientId, String message) throws MessengerApiException, MessengerIOException, MalformedURLException {

        List<String> urls = pictureParserService.getPhotoesUrlsByKeyWord(message);

        Boolean check=urls.isEmpty();
        if (check == false) {
            final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(RichMediaAsset.Type.IMAGE, new URL(urls.get(0)));
            super.sendMedia(recipientId, richMediaAsset);
        }else{
            final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(RichMediaAsset.Type.IMAGE, new URL("https://www.mygoyangi.com/wp-content/uploads/2016/11/download.jpeg"));
            super.sendMedia(recipientId, richMediaAsset);
        }
    }
}

