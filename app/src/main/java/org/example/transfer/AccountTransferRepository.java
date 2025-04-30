package org.example.transfer;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

@Repository
public class AccountTransferRepository {
    private final RedisTemplate<String, String> redisTemplate;

    AccountTransferRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean transfer(String fromUser, String toUser, long amount) {
        String fromKey = "user:" + fromUser;
        String toKey = "user:" + toUser;

        return redisTemplate.execute(new SessionCallback<>() {
            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(org.springframework.data.redis.core.RedisOperations operations) {
                operations.multi();
                operations.opsForValue().decrement(fromKey, amount);
                // .. 실패할 수 여지가 있다.
                operations.opsForValue().increment(toKey, amount);
                var result = operations.exec();

                // EXEC 결과가 null이면 실패 (WATCH 사용할 경우 필요, 여기서는 무조건 EXEC이라 체크는 의미 없음)
                return result != null;
            }
        });
    }
}
