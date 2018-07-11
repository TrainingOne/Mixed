package mixed.facebook.functionality;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class MessageHolder {
    private static volatile MessageHolder instance;

    private MessageHolder() {
    }

    public static MessageHolder getInstance() {
        MessageHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (MessageHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MessageHolder();
                }
            }
        }
        return localInstance;
    }

    private String userId;
    private String message;
    final static List<MessageHolder> messagearchive = new ArrayList<>();


    public void addToMessageArchive(String userid, String mes) {
        instance.setUserId(userid);
        instance.setMessage(mes);
        messagearchive.add(instance);
    }

    public String getLastMessage(String userId1) {
        List<MessageHolder> concreteUserMessage;
        concreteUserMessage = messagearchive.stream().filter(messageHolder -> messageHolder.getUserId().equalsIgnoreCase(userId1)).collect(Collectors.toList());
        if (!concreteUserMessage.isEmpty()) {
            return concreteUserMessage.get(concreteUserMessage.size() - 1).getMessage();
        } else return "No Message";
    }

}
