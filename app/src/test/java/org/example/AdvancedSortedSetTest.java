package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.io.Serializable;

class AdvancedSortedSetTest {

    RedissonClient redissonClient;

    @BeforeEach
    void setUp() {
        redissonClient = new RedissonConfig().getRedissonClient();
        redissonClient.getKeys().deleteByPattern("*");
    }


    @Test
    void sortedSetTest() {
        RScoredSortedSet<String> fruits = redissonClient.getScoredSortedSet("fruits");

        // 요소 추가 (score가 정렬 기준)
        fruits.add(1.0, "apple");
        fruits.add(2.0, "banana");
        fruits.add(3.0, "cherry");

        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }

    @Test
    void objectSortedSetTest() {
        RScoredSortedSet<Product> productSet = redissonClient.getScoredSortedSet("products");
        // 제품 추가 (score = 가격)
        productSet.add(1200.0, new Product("Keyboard", 1200.0));
        productSet.add(500.0, new Product("Mouse", 500.0));
        productSet.add(1500.0, new Product("Monitor", 1500.0));

        for (Product product : productSet) {
            System.out.println(product);
        }
    }

    public static class Product implements Serializable {
        private final String name;
        private final double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                   "name='" + name + '\'' +
                   ", price=" + price +
                   '}';
        }
    }

}
