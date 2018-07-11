package mixed.telega.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyKeyboardBuilder {


    public SendMessage buildReplyKeyboard(SendMessage sendMessage,  List<String> keyboardTitles){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<String> subTiltes1 = keyboardTitles.subList(0, keyboardTitles.size()/2 );
        List<String> subTiltes2 = keyboardTitles.subList(keyboardTitles.size()/2, keyboardTitles.size());

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        for (String string : subTiltes1){
            row1.add(string);
        }

        keyboard.add(row1);
        for (String string : subTiltes2){
            row2.add(string);
        }
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard).setOneTimeKeyboard(true);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
