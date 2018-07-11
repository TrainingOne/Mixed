package mixed.telega.senders;


import lombok.extern.slf4j.Slf4j;
import mixed.telega.FuriousBot;
import mixed.telega.services.InlineKeyboardBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


@Service
@Slf4j
public class MessageSender {

    private final FuriousBot furiousBot;

    private final InlineKeyboardBuilder inlineKeyboardBuilder;

    @Autowired
    public MessageSender (final FuriousBot furiousBot,
                          final InlineKeyboardBuilder inlineKeyboardBuilder){
        this.furiousBot = furiousBot;
        this.inlineKeyboardBuilder = inlineKeyboardBuilder;
    }

    public void sendImageFromChat(String f_id, String chatId, String caption){
        try {
            // Execute the method
            log.info("Trying to send " + f_id);
            furiousBot.sendPhoto( new SendPhoto().setPhoto(f_id).setChatId(chatId).setCaption(caption));
            log.info(f_id +" has been suqsessfully sended");
        } catch (TelegramApiRequestException exception) {
            log.warn(exception.getApiResponse());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(Long chat_id, String text) {
        try {
            furiousBot.execute(new SendMessage().setChatId(chat_id).setText(text));
        } catch (TelegramApiException e){
            e.printStackTrace();
            log.info(e.getCause().toString());
        }
    }

    public void sendMessage(SendMessage msg) {
        try {
            furiousBot.execute(msg);
        } catch (TelegramApiException e){
            e.printStackTrace();
            log.info(e.getCause().toString());
        }
    }


    public void forwardMessage(Integer message_id, Long chat_id_from, Long chat_id_to){
        try {
            furiousBot.execute(new ForwardMessage().setFromChatId(chat_id_from).setChatId(chat_id_to).setMessageId(message_id));
        }catch (TelegramApiException e){
            log.info(e.getMessage());
        }

    }

    /*
    Here you need just to pass your message, anf id of message, for what you want  to reply
     */
    public void sendMessageAsReplyTo(SendMessage sendMessage, Integer reply_to){
        try{
            log.info("We are in send reply to image");


            furiousBot.execute( sendMessage.setReplyToMessageId(reply_to));

        }catch (TelegramApiException e){
            log.warn(e.getMessage());
        }
    }

    public void sendInlineKeyboard(Update update, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId()).setText(text);
        InlineKeyboardMarkup markupInline = inlineKeyboardBuilder.buildInlineKeyboard(update.getMessage().getText());
        sendMessage.setReplyMarkup(markupInline);
        try {
            furiousBot.execute(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }











}
