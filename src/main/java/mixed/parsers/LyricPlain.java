package mixed.parsers;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class LyricPlain {

    private String name;
    private String link;

}
