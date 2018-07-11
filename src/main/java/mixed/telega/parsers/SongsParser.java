package mixed.telega.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SongsParser {

    public List<String> getSongsLyricsUrlsByKey(String key)  {

        String URL = "http://www.lyricsfreak.com/search.php?a=search&type=song&q=";

        //
        key = key.replaceAll(" ", "+");

        List<String> link = new ArrayList<String>();
        List<String> songs = new ArrayList<>();
        List<String > responces;

        try {
            Document doc = Jsoup.connect(URL + key)
                    .referrer(URL + key).get();

            doc.select("td > a.song").forEach(element ->
                    element.getElementsByTag("a").forEach(tag ->
                            songs.add(tag.text())));

            doc.select("a.song").forEach(song ->
                    link.add("http://www.lyricsfreak.com" + song.attr("href")));

        }catch (IOException e){
            log.info(e.getMessage());
        }

        Iterator iterator= link.iterator();

        responces = songs.stream().map(song -> song + " " + iterator.next()).collect(Collectors.toList());

        return responces;


    }
}
