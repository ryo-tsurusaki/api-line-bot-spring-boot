package jp.tsurusaki.application.interceptor;

import jp.tsurusaki.domain.service.OpenAiChatService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
@Slf4j
public class LineBotHandler {

  @Autowired
  private OpenAiChatService openAiChatService;

  /**
   * handle text message event.
   * @param event MessageEvent object.
   * @return Message object.
   */
  @EventMapping
  public Message handleTextMessageEvent(
      MessageEvent<TextMessageContent> event) {

    String originalMessageText = event.getMessage().getText();
    String replyMessageText = this.openAiChatService
        .openAiChat(originalMessageText);

    return new TextMessage(replyMessageText);
  }

  /**
   * handle default message event.
   * @param event Event object.
   */
  @EventMapping
  public void handleDefaultMessageEvent(Event event) {

    log.info("event: " + event);
  }
}
