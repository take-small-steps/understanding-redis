package org.example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash
public class Product implements Serializable {

    @Id
    String id;
    String name;

    private List<String> reviewIds = new ArrayList<>();

    public Product(String name, List<String> reviewIds) {
        this.name = name;
        this.reviewIds = reviewIds;
    }
}
