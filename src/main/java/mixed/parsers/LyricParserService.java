package mixed.parsers;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Service
public class LyricParserService {

    private List<LyricPlain> lyrics = new ArrayList<>();
    private LyricPlain lyricPlain = new LyricPlain();

    public List<LyricPlain> getSongsLyricsUrlsByKey(String key) throws IOException {
        String URL = "http://www.lyricsfreak.com/search.php?a=search&type=song&q=";
        key = key.replaceAll(" ", "+");
        List<String> link = new ArrayList<>();
        List<String> songs = new ArrayList<>();
        Document doc = Jsoup.connect(URL + key)
                .referrer(URL + key).get();

        doc.select("td > a.song").forEach(element ->
                element.getElementsByTag("a").forEach(tag ->
                        songs.add(tag.text())));

        doc.select("a.song").forEach(song ->
                link.add("http://www.lyricsfreak.com" + song.attr("href")));

        if (songs.isEmpty()) {
            return lyrics;
        } else {
            Iterator iterator = songs.iterator();

            link.forEach(element -> {

                lyricPlain.setLink(element);
                lyricPlain.setName((String) iterator.next());
                lyrics.add(lyricPlain);

            });
            return lyrics;
        }
    }
}
