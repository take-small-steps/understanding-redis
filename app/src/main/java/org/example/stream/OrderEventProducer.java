package org.example.stream;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderEventProducer {

    private final StringRedisTemplate redisTemplate;
    private static final String STREAM_KEY = "order.stream";

    public OrderEventProducer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishOrderEvent(String orderId, String status) {
        Map<String, String> message = new HashMap<>();
        message.put("orderId", orderId);
        message.put("status", status);

        redisTemplate.opsForStream().add(STREAM_KEY, message);
    }
}