package mixed.telega;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import mixed.telega.services.DecisionMaker;
import mixed.telega.services.UpdatesRequestExecutor;

@Service
@Slf4j
public class FuriousBot extends TelegramLongPollingBot {

    @Autowired
    private UpdatesRequestExecutor executor;

    @Autowired
    private DecisionMaker decisionMaker;

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage()){

            if (update.getMessage().hasPhoto()) {

              decisionMaker.executePhoto(update);

            }else if (update.getMessage().hasDocument()){

              executor.sendInline(update);

            }else if (update.getMessage().hasText()) {
                log.info("       "+update.getMessage().getChatId());

                decisionMaker.executeText(update);

            }

        }else  if(update.hasCallbackQuery()){

            decisionMaker.executeCallbackQuery(update);

        }else  if (update.hasInlineQuery()){
            log.info("Inline  query : " + update.getInlineQuery().getQuery());
            executor.sendReply(update);
        }


    }


    @Override
    public String getBotUsername() {
        return "MyFuriousBot";
    }

    @Override
    public String getBotToken() {
        return "402476540:AAEwHy4LNFdAmLP9I8H5u_22J_1z5OsINcQ";
    }


}
