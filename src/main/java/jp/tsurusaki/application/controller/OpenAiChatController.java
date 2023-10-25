package jp.tsurusaki.application.controller;

import jp.tsurusaki.domain.service.OpenAiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiChatController {

    @Autowired
    private OpenAiChatService openAiChatService;

    @GetMapping("/openAiChat")
    public ResponseEntity<String> openAiChat(@RequestParam(name = "message_text")String messageText) {

        return ResponseEntity.ok(this.openAiChatService.openAiChat(messageText));
    }
}
