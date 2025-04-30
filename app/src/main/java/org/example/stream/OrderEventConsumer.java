package org.example.stream;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class OrderEventConsumer {

    private final StringRedisTemplate redisTemplate;
    private static final String STREAM_KEY = "order.stream";
    private static final String GROUP_NAME = "order-group";
    private static final String CONSUMER_NAME = "consumer-1";

    public OrderEventConsumer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initConsumerGroup() {
        try {
            redisTemplate.opsForStream().createGroup(STREAM_KEY, GROUP_NAME);
        } catch (Exception e) {
            // 이미 그룹이 있을 경우 무시
        }

        startConsumerThread();
    }

    private void startConsumerThread() {
        new Thread(() -> {
            while (true) {
                List<MapRecord<String, Object, Object>> messages =
                        redisTemplate.opsForStream().read(
                                Consumer.from(GROUP_NAME, CONSUMER_NAME),
                                StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed())
                        );

                if (messages != null) {
                    for (MapRecord<String, Object, Object> message : messages) {
                        String orderId = message.getValue().get("orderId").toString();
                        String status = message.getValue().get("status").toString();
                        System.out.println("🟢 Received: orderId=" + orderId + ", status=" + status);

                        // 처리 완료 후 ACK
                        redisTemplate.opsForStream().acknowledge(STREAM_KEY, GROUP_NAME, message.getId());
                    }
                }
            }
        }).start();
    }
}