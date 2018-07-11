package mixed.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PictureParserService {

    public List<String> getPhotoesUrlsByKeyWord(String key){
        String mainkeyword = key.replaceAll(" ", "+").toLowerCase();
        String URL = "https://www.pexels.com/search/" + mainkeyword + "/";

        List<String > urls = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(URL + key)
                    .referrer(URL + key).get();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        doc.getElementsByClass("photo-item").forEach(element ->
                element.getElementsByTag("a").forEach(tag ->
                        tag.getElementsByTag("img").forEach(img ->
                        {
                            urls.add(img.attr("src"));
                            log.info(img.attr("src"));

                        })));


        return urls;

    }
}
