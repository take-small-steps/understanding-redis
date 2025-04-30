package org.example.stream;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderEventProducer producer;

    public OrderController(OrderEventProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/orders")
    public String createOrder(@RequestParam String orderId) {
        producer.publishOrderEvent(orderId, "CREATED");
        return "Order published";
    }
}