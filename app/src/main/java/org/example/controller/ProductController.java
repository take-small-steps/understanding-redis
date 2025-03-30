package org.example.controller;

import org.example.domain.Product;
import org.example.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 저장
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.ok(saved);
    }

    // 조회 (캐시 적용)
    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable String id) throws InterruptedException {
        System.out.println("input >>> " + id);
        Product found = productService.getProduct(id);
        return ResponseEntity.ok(found);
    }

    // 수정 (캐시 갱신)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Product product) {
        productService.updateProduct(id, product);
        return ResponseEntity.noContent().build();
    }

    // 전체 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}