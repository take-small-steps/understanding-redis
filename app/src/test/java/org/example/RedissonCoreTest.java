/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;

class RedissonCoreTest {
    RedissonClient redissonClient;

    @BeforeEach
    void setUp() {
        redissonClient = new RedissonConfig().getRedissonClient();
    }

    @Test
    void list() {

        RList<String> helloList = redissonClient.getList("Hello");

        helloList.add("A");
        helloList.add("B");
        helloList.add("C");
        helloList.add("D");
        helloList.add("E");

        for (String o : helloList) {
            System.out.println(o);
        }
    }

    @Test
    void queue() {
        RQueue<String> queue = redissonClient.getQueue("myQueue");

        queue.add("first");
        queue.add("second");
        queue.add("third");

        // FIFO 방식으로 값 꺼내기 (Dequeue)
        System.out.println(queue.poll()); // first
        System.out.println(queue.poll()); // second
        System.out.println(queue.poll()); // third

    }

    @Test
    void blockingQueueMultiThreadTest() throws InterruptedException {
        RBlockingQueue<String> queue = redissonClient.getBlockingQueue("myBlockingQueue");

        // 생산자 스레드
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer("Item-" + i);
                System.out.println("Produced: Item-" + i);
                try {
                    Thread.sleep(500); // 생산 속도 조절
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 소비자 스레드
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    String item = queue.take();
                    System.out.println("Consumed: " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    @Test
    void hashTest() {
        // Redis의 Hash 자료구조를 RMap으로 사용
        RMap<String, String> hash = redissonClient.getMap("myHash");

        // 데이터 삽입
        hash.put("name", "John Doe");
        hash.put("email", "john.doe@example.com");
        hash.put("role", "admin");

        // 데이터 조회
        String name = hash.get("name");
        String email = hash.get("email");

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);

        // 전체 데이터 순회
        for (java.util.Map.Entry<String, String> entry : hash.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // 데이터 삭제
        hash.remove("role");

        // 크기 출력
        System.out.println("Hash size: " + hash.size());
    }

    @Test
    void SetTest() {
        RSet<String> set = redissonClient.getSet("mySet");

        // 값 추가 (중복 불가)
        set.add("apple");
        set.add("banana");
        set.add("orange");
        set.add("apple"); // 중복 -> 저장되지 않음

        // 값 확인
        for (String item : set) {
            System.out.println("Set Item: " + item);
        }

        // 포함 여부 확인
        boolean hasBanana = set.contains("banana");
        System.out.println("Contains banana: " + hasBanana);

        // 삭제
        set.remove("orange");
        System.out.println("Set size: " + set.size());
    }

    @Test
    void sortedSetTest() {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet("mySortedSet");

        // 값 추가 (score와 함께)
        sortedSet.add(1.0, "one");
        sortedSet.add(3.0, "three");
        sortedSet.add(2.0, "two");

        // score 순으로 자동 정렬
        for (String item : sortedSet) {
            System.out.println("SortedSet Item: " + item);
        }

        // 특정 score 범위 조회
        System.out.println("Range by score (1.0~2.0): " + sortedSet.valueRange(1.0, true, 2.0, true));

        // 삭제
        sortedSet.remove("three");
        System.out.println("SortedSet size: " + sortedSet.size());
    }

    @Test
    void hyperLogLogTest() {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog("myHyperLogLog");

        // 값 추가
        hyperLogLog.add("user1");
        hyperLogLog.add("user2");
        hyperLogLog.add("user3");
        hyperLogLog.add("user2"); // 중복 값
        hyperLogLog.add("user4");

        // 고유값 개수 추정
        long count = hyperLogLog.count();
        System.out.println("HyperLogLog estimated unique count: " + count);

        // 병합도 가능 (다른 HyperLogLog와 merge)
        RHyperLogLog<String> hyperLogLog2 = redissonClient.getHyperLogLog("myHyperLogLog2");
        hyperLogLog2.add("user5");

        hyperLogLog.mergeWith(String.valueOf(hyperLogLog2));
        System.out.println("After merge estimated unique count: " + hyperLogLog.count());
    }
}
