package mixed.telega.executors;


import lombok.extern.slf4j.Slf4j;
import mixed.telega.services.UpdatesRequestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Service
@Slf4j
public class TextExecutor {

    @Autowired
    private UpdatesRequestExecutor executor;

    public void executeText(Update update){

        String text = update.getMessage().getText();
        Message message = update.getMessage();

        switch (text){

            case "Hi" : {
                executor.doSendForHello(message);
                break;
            }
            case "Hello" :{
                executor.doSendForHello(message);
                break;
            }
            case "hello" :{
                executor.doSendForHello(message);
                break;
            }
            case "Hello!" :{
                executor.doSendForHello(message);
                break;
            }
            case "Hi!"  :{
                executor.doSendForHello(message);
                break;
            }
            case "hi" :{
                executor.doSendForHello(message);
                break;
            }
            case "help" :{
                executor.doSendForHello(message);
                break;
            }
            case "Help":{
                executor.doSendForHello(message);
                break;
            }
            default:  {

                executor.sendInline(update);
                //executor.sendButtons(update);

            }
        }
    }


}
