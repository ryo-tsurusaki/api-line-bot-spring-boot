package jp.tsurusaki.domain.service;

import jp.tsurusaki.domain.repository.OpenAIChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenAiChatService {

    @Autowired
    private OpenAIChatRepository openAIChatRepository;

    /**
     * openAiChat
     * @param messageText request message text.
     * @return reply message text.
     */
    public String openAiChat(String messageText) {

        return this.openAIChatRepository.openAiChat(messageText).getContent();
    }
}
