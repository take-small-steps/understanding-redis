package org.example.service;

import org.example.domain.Product;
import org.example.domain.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Cacheable(value = "product", key = "#id")
    public Product  getProduct(String id) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println(">> Loading from DB" + id);
        return productRepository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "product", key = "#id")
    public void updateProduct(String id, Product newProduct) {
        productRepository.findById(id).orElseThrow();
        productRepository.save(newProduct);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}
