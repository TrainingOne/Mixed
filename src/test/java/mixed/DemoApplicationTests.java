package mixed;

import junit.framework.TestCase;
import mixed.telega.parsers.PhotoParser;
import mixed.telega.parsers.SongsParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests extends TestCase {


    @Autowired
    private PhotoParser photoParser;
    @Autowired
    private SongsParser songsParser;

    @Test
    public void photoParsesClearly(){

        List<String> text = Arrays.asList("Sunset", "Day",  "Deer");

        List<List<String>> results = new ArrayList<>();

        List<String> result1 = Arrays.asList("https://images.pexels.com/photos/1210543/pexels-photo-1210543.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1161161/pexels-photo-1161161.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1209658/pexels-photo-1209658.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1209749/pexels-photo-1209749.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1210305/pexels-photo-1210305.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1209999/pexels-photo-1209999.jpeg?auto=compress&cs=tinysrgb&h=350");

        List<String> result2 = Arrays.asList("https://images.pexels.com/photos/1203842/pexels-photo-1203842.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1028707/pexels-photo-1028707.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/853199/pexels-photo-853199.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/1210062/pexels-photo-1210062.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/196664/pexels-photo-196664.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/235941/pexels-photo-235941.jpeg?auto=compress&cs=tinysrgb&h=350");

        List<String> result4 = Arrays.asList("https://images.pexels.com/photos/34231/antler-antler-carrier-fallow-deer-hirsch.jpg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/219906/pexels-photo-219906.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/535431/pexels-photo-535431.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/14762/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/257558/pexels-photo-257558.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://images.pexels.com/photos/839303/pexels-photo-839303.jpeg?auto=compress&cs=tinysrgb&h=350");

        results.add(result1);
        results.add(result2);
        results.add(result4);


        Iterator iterator = results.iterator();

        for (String str : text){

            assertEquals( iterator.next(), photoParser.getPhotosUrlsByKeyWord(str).subList(0,6) );


        }

    }

    @Test
    public void lyricsParsesClearly(){


        List<String> lyrics = Arrays.asList("Cure", "Moon", "Deep", "Sick");

        List<List<String>> results = new ArrayList<>();

        List<String> result1 = Arrays.asList("Cure lyrics http://www.lyricsfreak.com/m/metallica/cure_20092065.html",
                "Cure lyrics http://www.lyricsfreak.com/d/darling+violetta/cure_20175329.html",
                "The Cure lyrics http://www.lyricsfreak.com/m/mekong+delta/the+cure_20175259.html",
                "The Cure lyrics http://www.lyricsfreak.com/n/national+napalm+syndicate/the+cure_20755863.html",
                "The Cure lyrics http://www.lyricsfreak.com/j/jordin+sparks/the+cure_20842045.html",
                "The Cure lyrics http://www.lyricsfreak.com/j/j+cole/the+cure_21032110.html");

        List<String> result2 = Arrays.asList("Mooning lyrics http://www.lyricsfreak.com/g/grease/mooning_20581266.html" ,
                "The Moon lyrics http://www.lyricsfreak.com/j/joshua+payne/the+moon_10154003.html",
                "Moon lyrics http://www.lyricsfreak.com/a/ayumi+hamasaki/moon_20819249.html",
                "Moon lyrics http://www.lyricsfreak.com/d/dada/moon_20258343.html",
                "The Moon lyrics http://www.lyricsfreak.com/x/x+perience/the+moon_20260747.html" ,
                "Moon lyrics http://www.lyricsfreak.com/r/rusted+root/moon_20260252.html");

        List<String> result3 = Arrays.asList("Deep lyrics http://www.lyricsfreak.com/a/anathema/deep_10010505.html",
                "Deep lyrics http://www.lyricsfreak.com/c/charlton+hill/deep_20186541.html",
                "The Deep lyrics http://www.lyricsfreak.com/f/further+seems+forever/the+deep_20057539.html" ,
                "Deep lyrics http://www.lyricsfreak.com/d/danzig/deep_20036163.html",
                "Deep lyrics http://www.lyricsfreak.com/p/paradise+lost/deep_20104554.html",
                "Deep lyrics http://www.lyricsfreak.com/n/nickelback/deep_20100428.html");


        List<String> result4 = Arrays.asList("The Sickness lyrics http://www.lyricsfreak.com/3/3x+krazy/the+sickness_20718324.html",
                "Sickness lyrics http://www.lyricsfreak.com/g/grey+daze/sickness_20167952.html",
                "Sick lyrics http://www.lyricsfreak.com/d/dope/sick_10088772.html",
                "Sickness lyrics http://www.lyricsfreak.com/s/superjoint+ritual/sickness_20167981.html",
                "Sickness lyrics http://www.lyricsfreak.com/i/iggy+pop/sickness_20066890.html",
                "Sick lyrics http://www.lyricsfreak.com/s/son+of+dork/sick_20470549.html");

        results.add(result1);
        results.add(result2);
        results.add(result3);
        results.add(result4);

        Iterator iterator = results.iterator();

        for (String str : lyrics){

            assertEquals(iterator.next(), songsParser.getSongsLyricsUrlsByKey(str).subList(0,6));
        }
    }



}
