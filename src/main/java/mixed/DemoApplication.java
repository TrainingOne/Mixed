package mixed;

import com.github.messenger4j.Messenger;
import lombok.extern.slf4j.Slf4j;
import mixed.telega.FuriousBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@SpringBootApplication
@Slf4j
public class DemoApplication  {


    @Bean
    public Messenger messenger(@Value("${messenger4j.pageAccessToken}") String pageAccessToken,
                               @Value("${messenger4j.appSecret}") final String appSecret,
                               @Value("${messenger4j.verifyToken}") final String verifyToken) {
        return Messenger.create(pageAccessToken, appSecret, verifyToken);
    }


    public static void main(String[] args) {
        ApiContextInitializer.init(); // Here we initialize our API

        TelegramBotsApi botapi = new TelegramBotsApi();

        ConfigurableApplicationContext annotationConfigApplicationContext = SpringApplication.run(DemoApplication.class, args);

        FuriousBot furiousBot = (FuriousBot ) annotationConfigApplicationContext.getBean("furiousBot");

        try {
            botapi.registerBot(furiousBot);
        } catch (TelegramApiException e) {
            log.warn(e.getMessage());
        }



    }


}
