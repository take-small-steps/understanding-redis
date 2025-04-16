package org.example.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class ChatPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    ChatPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
