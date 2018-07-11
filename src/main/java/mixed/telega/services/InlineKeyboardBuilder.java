package mixed.telega.services;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InlineKeyboardBuilder {


    public InlineKeyboardMarkup buildInlineKeyboard(String text){

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("Find photo","photo"+text);
        map.put("Find lyrics", "lyric"+text);
        List<InlineKeyboardButton> rowInline = buildInlineTextButtons(map);

        rowsInline.add(rowInline);

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    public List<InlineKeyboardButton> buildInlineTextButtons(Map<String, String> buttonsMap){

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        buttonsMap.forEach( (k,v) ->  buttons.add(new InlineKeyboardButton().setText(k).setCallbackData(v)) );

        return buttons;
    }

    public List<InlineKeyboardButton> buildInlineUrlButttons(Map<String, String> buttonsMap){

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        buttonsMap.forEach( (k,v) ->  buttons.add(new InlineKeyboardButton().setText(k).setUrl(v)) );

        return buttons;

    }
}
