package mixed.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
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
            e.printStackTrace();
        }

        for (Element tracks : doc.getElementsByClass("photo-item")) {
            for (Element tracs : tracks.getElementsByTag("a")){
                for (Element  element : tracs.getElementsByTag("img")) {
                    urls.add(element.attr("src"));
                }
            }
        }

        return urls;

    }
}
