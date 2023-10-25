package jp.tsurusaki.domain.repository;

import jp.tsurusaki.domain.model.OpenAiChatData;

public interface OpenAIChatRepository {

    /**
     * execute openAiChat
     * @param messageText request message text.
     * @return OpenAiChatData object.
     */
    OpenAiChatData openAiChat(String messageText);
}
