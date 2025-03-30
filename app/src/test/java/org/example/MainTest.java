package org.example;

import org.example.domain.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class MainTest {

    @Autowired
    ProductService productService;

    @BeforeEach
    void setUp() {
        Product foo = new Product("foo", new ArrayList<>());
        productService.saveProduct(foo);
    }

    @Test
    void name() {


    }

    @AfterEach
    void tearDown() {
        productService.deleteAll();

    }
}
