package com.promptcourse.config;

import com.promptcourse.entity.Product;
import com.promptcourse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            // Thêm một số sản phẩm mẫu
            Product product1 = new Product();
            product1.setName("Laptop Dell XPS 15");
            product1.setDescription("Laptop cao cấp với màn hình 15 inch, RAM 16GB, SSD 512GB");
            product1.setPrice(new BigDecimal("25000000"));
            product1.setStockQuantity(10);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("iPhone 15 Pro");
            product2.setDescription("Điện thoại thông minh với chip A17 Pro, camera 48MP");
            product2.setPrice(new BigDecimal("28000000"));
            product2.setStockQuantity(20);
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("Samsung Galaxy S24");
            product3.setDescription("Điện thoại Android với màn hình AMOLED 6.2 inch");
            product3.setPrice(new BigDecimal("22000000"));
            product3.setStockQuantity(15);
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("MacBook Pro M3");
            product4.setDescription("Laptop Apple với chip M3, màn hình Retina 14 inch");
            product4.setPrice(new BigDecimal("45000000"));
            product4.setStockQuantity(5);
            productRepository.save(product4);

            Product product5 = new Product();
            product5.setName("AirPods Pro 2");
            product5.setDescription("Tai nghe không dây với chống ồn chủ động");
            product5.setPrice(new BigDecimal("6500000"));
            product5.setStockQuantity(30);
            productRepository.save(product5);
        }
    }
}
