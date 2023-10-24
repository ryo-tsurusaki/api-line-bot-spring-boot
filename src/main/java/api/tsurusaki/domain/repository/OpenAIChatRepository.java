package api.tsurusaki.domain.repository;

public interface OpenAIChatRepository {

    /**
     * execute openAiChat
     * @param messageText request message text.
     * @return reply message text.
     */
    String openAiChat(String messageText);
}
