package api.tsurusaki.domain.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    /**
     * create reply message text.
     * @param message request message text.
     * @return reply message text.
     */
    public String createReplyMessageText(String message) {

        return "レスポンス: ".concat(message);
    }
}
