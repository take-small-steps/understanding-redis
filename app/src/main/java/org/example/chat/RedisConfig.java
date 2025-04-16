package org.example.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {



    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> stringObjectRedisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.start();

        stringObjectRedisTemplate.setConnectionFactory(connectionFactory);
        return stringObjectRedisTemplate;
    }
}