package mixed.telega.services;


import lombok.extern.slf4j.Slf4j;
import mixed.telega.FuriousBot;
import mixed.telega.executors.CallbackQueryExecutor;
import mixed.telega.executors.TextExecutor;
import mixed.telega.senders.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;

@Service
@Slf4j
public class DecisionMaker {

    private final CallbackQueryExecutor callbackQueryExecutor;

    private final TextExecutor textExecutor;

    @Autowired
    public DecisionMaker( CallbackQueryExecutor callbackQueryExecutor,
                         TextExecutor textExecutor)
    {
        this.callbackQueryExecutor = callbackQueryExecutor;
        this.textExecutor = textExecutor;
    }


    public void executeText(Update update){

        log.info(update.getMessage().getText());
        String text = update.getMessage().getText();

        textExecutor.executeText(update);

    }

    public void executePhoto(Update update){


    }

    public void executeAudio(Update update){


    }

    public void executeDocument(Update update){


    }

    public void executeCallbackQuery(Update update){

        callbackQueryExecutor.executeCallback(update);
    }
}
