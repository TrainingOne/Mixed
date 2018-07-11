package mixed.telega.executors;


import lombok.extern.slf4j.Slf4j;
import mixed.telega.services.UpdatesRequestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;

@Service
@Slf4j
public class CallbackQueryExecutor {

    private final UpdatesRequestExecutor executor;

    @Autowired
    public CallbackQueryExecutor(UpdatesRequestExecutor executor) {
        this.executor = executor;
    }

    public void executeCallback(Update update){

        String text = update.getCallbackQuery().getData();
        String searchBy = text.substring(5, text.length());

        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        String call_data = update.getCallbackQuery().getData().substring(0,5);
       switch (call_data){

           case "photo" : {
               log.info( text);
                executor.findAndSendPhotoByKey(chat_id, searchBy);
               break;
           }
           case "lyric" :{
               log.info(text);
                executor.findAndSendSongLyrics(chat_id, searchBy);

               break;
           }
       }
    }
}
