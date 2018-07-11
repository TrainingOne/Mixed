package mixed.telega.services;


import lombok.extern.slf4j.Slf4j;
import mixed.parsers.LyricParserService;
import mixed.parsers.LyricPlain;
import mixed.parsers.PictureParserService;
import mixed.telega.FuriousBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import mixed.telega.parsers.PhotoParser;
import mixed.telega.parsers.SongsParser;
import mixed.telega.senders.MessageSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UpdatesRequestExecutor {

    private final MessageSender messageSender;

    private final PictureParserService photoParser;

    private final LyricParserService songsParser;

    private final ReplyKeyboardBuilder replyKeyboardBuilder;

    @Autowired
    public UpdatesRequestExecutor( MessageSender messageSender,
                                  PictureParserService photoParser,
                                  LyricParserService songsParser,
                                  ReplyKeyboardBuilder replyKeyboardBuilder) {
        this.messageSender = messageSender;
        this.photoParser = photoParser;
        this.songsParser = songsParser;
        this.replyKeyboardBuilder = replyKeyboardBuilder;
    }

    public void findAndSendSongLyrics(Long chatId, String textFromMessage){

        try {

            List<String> link;
            List<LyricPlain> lyricPlains =  songsParser.getSongsLyricsUrlsByKey(textFromMessage) ;
            link = lyricPlains.stream().map(lyricPlain -> lyricPlain.getName() + " " + lyricPlain.getLink()).collect(Collectors.toList());

            if (link.isEmpty()) {

                messageSender.sendMessage(new SendMessage().setChatId(chatId).setText("Sorry, we don`t find it(("));
            } else {

                for (String url : link){
                    log.info(url);
                }
                int i = 0;

                for (String lined : link) {
                    if (i < 1) {

                        String fid = "http://virtu-well.com/wp-content/uploads/2017/07/pexels-photo-42379.jpeg";
                        messageSender.sendImageFromChat(fid, chatId.toString(),lined);
                        i++;
                    }
                }
            }
        }catch (Exception e) {
            log.warn(e.getCause().toString());
        }
    }

    public void  findAndSendPhotoByKey(long  chat_id, String textFromMessage){
        log.info("Find and send  photo by key");

        List<String> urls = photoParser.getPhotoesUrlsByKeyWord(textFromMessage);

        log.info("already parsed urls");

          if (!urls.isEmpty() ) {
                int count = urls.size();
                if (urls.size() > 2){
                    count = count /2;
                }
                if ( count > 3 ) count = 3;
                urls = urls.subList(0, count);
              for (String element : urls) {
                  messageSender.sendImageFromChat(element, ""+chat_id, "");
              }

          }else messageSender.sendMessage(chat_id,"Sorry, but these photos are sleeping. Please, don`t wake them up!");

    }

    public void resendPhotoFromChat(Update update){
        Message msg = update.getMessage();
        List<PhotoSize> photos = msg.getPhoto();
        String fid = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst().orElse(null).getFileId();
        messageSender.sendImageFromChat(fid, msg.getChatId().toString(),"Some");

    }

    public void sendButtons(Update update){

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(update.getMessage().getChatId())
                .setText("Here is your keyboard");
        // Create ReplyKeyboardMarkup object
       message = replyKeyboardBuilder.buildReplyKeyboard(message, Arrays.asList("Cool", "New", "Best", "WTF?"));
        // Add it to the message

        messageSender.sendMessage(message); // Sending our message object to user
    }

    public void sendReply(Update update){

        log.info("We are in send reply");
        messageSender.sendMessageAsReplyTo(new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("What do you want to find?"),
                        update.getMessage().getMessageId());
    }

    public void  sendInline(Update update){

        messageSender.sendInlineKeyboard(update,"What do you  want to find?? ");
    }

    public void doSendForHello(Message message){

        SendMessage  message1 = new SendMessage().setChatId(message.getChatId()).setText("Hello, ! am cooool bot and I am here to search good photos or lyrics for you! All you need is just type topik of photo or song name, and than choose, what you are searching for");
        messageSender.sendMessage(message1);
    }
}
