package mixed.facebook.handlers;

import com.github.messenger4j.webhook.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FallBackHandler {
    private static final Logger logger = LoggerFactory.getLogger(FallBackHandler.class);


    @Autowired
    public FallBackHandler() {
    }

    public void handleFallbackEvent(Event event) {
        logger.debug("Handling FallbackEvent");
        final String senderId = event.senderId();
        logger.debug("senderId: {}", senderId);

        logger.info("Received unsupported message from user '{}'", senderId);
    }
}
