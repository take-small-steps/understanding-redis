package org.example.transfer;

import jakarta.el.ExpressionFactory;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Repository
public class AccountTransferLuaScriptRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String TRANSFER_LUA_SCRIPT = """
            local fromBalance = tonumber(redis.call('GET', KEYS[1]))
            if fromBalance == nil then
                return -1
            end
            if fromBalance < tonumber(ARGV[1]) then
                return 0
            end
            redis.call('DECRBY', KEYS[1], ARGV[1])
            redis.call('INCRBY', KEYS[2], ARGV[1])
            return 1
            """;

    public AccountTransferLuaScriptRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int transferWithLua(String fromUser, String toUser, long amount) {
        String fromKey = "user:" + fromUser;
        String toKey = "user:" + toUser;
        DefaultRedisScript<Integer> script = new DefaultRedisScript<>();
        script.setScriptText(TRANSFER_LUA_SCRIPT);
        script.setResultType(Integer.class);

        Integer execute = redisTemplate.execute(script,
                Arrays.asList(fromKey, toKey),
                String.valueOf(amount));

        return execute;
    }
}
