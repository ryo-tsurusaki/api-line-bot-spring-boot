package api.tsurusaki.domain.service;

import api.tsurusaki.domain.repository.OpenAIChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return this.openAIChatRepository.openAiChat(messageText);
    }
}
