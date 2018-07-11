package mixed.facebook.controller;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import lombok.extern.slf4j.Slf4j;
import mixed.facebook.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.github.messenger4j.Messenger.*;
import static java.util.Optional.of;


@RestController
@RequestMapping("/calltalk")
@Slf4j
public class PlatformCallBackController {

    /**
     * This is the main class for inbound and outbound communication with the Facebook Messenger Platform. The callback
     * handler is responsible for the webhook verification and processing of the inbound messages and events.
     */


    private final Messenger messenger;
    private final TextMessageHandler textHandler;
    private final AttachmentHandler attachmentHandler;
    private final QuickReplyHandler quickReplyHandler;
    private final PostBackHandler postBackHandler;
    private final AccountLinkingHandler accountLinkingHandler;
    private final OptInHandler optInHandler;
    private final MessageEchoHandler messageEchoHandler;
    private final MessageDeliveryHandler messageDeliveryHandler;
    private final MessageReadHandler messageReadHandler;
    private final FallBackHandler fallBackHandler;

    @Autowired
    public PlatformCallBackController(final Messenger messenger,
                                      final TextMessageHandler textHandler,
                                      final AttachmentHandler attachmentHandler,
                                      final QuickReplyHandler quickReplyHandler,
                                      final PostBackHandler postBackHandler,
                                      final AccountLinkingHandler accountLinkingHandler,
                                      final OptInHandler optInHandler,
                                      final MessageEchoHandler messageEchoHandler,
                                      final MessageDeliveryHandler messageDeliveryHandler,
                                      final MessageReadHandler messageReadHandler,
                                      final FallBackHandler fallBackHandler) {

        this.messenger = messenger;
        this.textHandler = textHandler;
        this.attachmentHandler = attachmentHandler;
        this.quickReplyHandler = quickReplyHandler;
        this.postBackHandler = postBackHandler;
        this.accountLinkingHandler = accountLinkingHandler;
        this.optInHandler = optInHandler;
        this.messageEchoHandler = messageEchoHandler;
        this.messageDeliveryHandler = messageDeliveryHandler;
        this.messageReadHandler = messageReadHandler;
        this.fallBackHandler = fallBackHandler;
    }

    /**
     * Webhook verification endpoint. <p> The passed verification token (as query parameter) must match the configured
     * verification token. In case this is true, the passed challenge string must be returned by this endpoint.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) final String mode,
                                                @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) final String verifyToken, @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) final String challenge) {
        log.debug("Received Webhook verification request - mode: {} | verifyToken: {} | challenge: {}", mode, verifyToken, challenge);
        try {
            this.messenger.verifyWebhook(mode, verifyToken);
            return ResponseEntity.ok(challenge);
        } catch (MessengerVerificationException e) {
            log.warn("Webhook verification failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Callback endpoint responsible for processing the inbound messages and events.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> handleCallback(@RequestBody final String payload, @RequestHeader(SIGNATURE_HEADER_NAME) final String signature) {
        log.debug("Received Messenger Platform callback - payload: {} | signature: {}", payload, signature);
        try {
            this.messenger.onReceiveEvents(payload, of(signature), event -> {
                if (event.isTextMessageEvent()) {
                    textHandler.handleTextMessageEvent(event.asTextMessageEvent());
                } else if (event.isAttachmentMessageEvent()) {
                    attachmentHandler.handleAttachmentMessageEvent(event.asAttachmentMessageEvent());
                } else if (event.isQuickReplyMessageEvent()) {
                    try {
                        quickReplyHandler.handleQuickReplyMessageEvent(event.asQuickReplyMessageEvent());
                    } catch (IOException e) {
                       log.warn(e.getMessage());
                    } catch (MessengerIOException e) {
                        log.warn(e.getMessage());
                    }
                } else if (event.isPostbackEvent()) {
                    postBackHandler.handlePostbackEvent(event.asPostbackEvent());
                } else if (event.isAccountLinkingEvent()) {
                    accountLinkingHandler.handleAccountLinkingEvent(event.asAccountLinkingEvent());
                } else if (event.isOptInEvent()) {
                    optInHandler.handleOptInEvent(event.asOptInEvent());
                } else if (event.isMessageEchoEvent()) {
                    messageEchoHandler.handleMessageEchoEvent(event.asMessageEchoEvent());
                } else if (event.isMessageDeliveredEvent()) {
                    messageDeliveryHandler.handleMessageDeliveredEvent(event.asMessageDeliveredEvent());
                } else if (event.isMessageReadEvent()) {
                    messageReadHandler.handleMessageReadEvent(event.asMessageReadEvent());
                } else {
                    fallBackHandler.handleFallbackEvent(event);
                }
            });
            log.debug("Processed callback payload successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            log.warn("Processing of callback payload failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}


