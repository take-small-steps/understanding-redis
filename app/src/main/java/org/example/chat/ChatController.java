package org.example.chat;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatPublisher chatPublisher;

    public ChatController(ChatPublisher chatPublisher) {
        this.chatPublisher = chatPublisher;
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String message) {
        chatPublisher.publish("chat.channel", message);
        return "메시지 전송 완료";
    }
}