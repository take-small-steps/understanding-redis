/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        RedissonClient redisson = new RedissonConfig().getRedissonClient();
        RBucket<String> foo = redisson.getBucket("foo");

        foo.set("Hello World!");
        String value = foo.get();
        System.out.println(value);
        redisson.shutdown();
    }
}
