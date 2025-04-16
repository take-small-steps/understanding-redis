package org.example.chat;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
class RedisSubscriberConfig {

    private final ChatSubscriber chatSubscriber;
    private final RedisMessageListenerContainer container;

    RedisSubscriberConfig(ChatSubscriber chatSubscriber, RedisMessageListenerContainer container) {
        this.chatSubscriber = chatSubscriber;
        this.container = container;
    }

    @PostConstruct
    public void init() {
        container.addMessageListener(chatSubscriber, new ChannelTopic("chat.channel"));
    }
}
