package api.tsurusaki;

import api.tsurusaki.domain.service.ChatService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@LineMessageHandler
@SpringBootApplication
public class Application {

    @Autowired
    private ChatService chatService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        String originalMessageText = event.getMessage().getText();
        String replyMessageText = this.chatService.createReplyMessageText(originalMessageText);
        return new TextMessage(replyMessageText);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
