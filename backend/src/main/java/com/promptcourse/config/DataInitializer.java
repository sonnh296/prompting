package com.promptcourse.config;

import com.promptcourse.entity.Product;
import com.promptcourse.entity.User;
import com.promptcourse.repository.ProductRepository;
import com.promptcourse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeProducts();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            // User mặc định để test
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(admin);

            User user1 = new User();
            user1.setUsername("testuser");
            user1.setEmail("test@example.com");
            user1.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("demo");
            user2.setEmail("demo@example.com");
            user2.setPassword(passwordEncoder.encode("demo123"));
            userRepository.save(user2);

            System.out.println("✅ Đã tạo " + userRepository.count() + " user mặc định");
            System.out.println("   - admin / admin123");
            System.out.println("   - testuser / password123");
            System.out.println("   - demo / demo123");
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            // Thêm nhiều sản phẩm mẫu
            Product product1 = new Product();
            product1.setName("Laptop Dell XPS 15");
            product1.setDescription("Laptop cao cấp với màn hình 15 inch, RAM 16GB, SSD 512GB, Intel Core i7");
            product1.setPrice(new BigDecimal("25000000"));
            product1.setStockQuantity(10);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("iPhone 15 Pro");
            product2.setDescription("Điện thoại thông minh với chip A17 Pro, camera 48MP, màn hình 6.1 inch");
            product2.setPrice(new BigDecimal("28000000"));
            product2.setStockQuantity(20);
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("Samsung Galaxy S24");
            product3.setDescription("Điện thoại Android với màn hình AMOLED 6.2 inch, Snapdragon 8 Gen 3");
            product3.setPrice(new BigDecimal("22000000"));
            product3.setStockQuantity(15);
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("MacBook Pro M3");
            product4.setDescription("Laptop Apple với chip M3, màn hình Retina 14 inch, RAM 16GB, SSD 512GB");
            product4.setPrice(new BigDecimal("45000000"));
            product4.setStockQuantity(5);
            productRepository.save(product4);

            Product product5 = new Product();
            product5.setName("AirPods Pro 2");
            product5.setDescription("Tai nghe không dây với chống ồn chủ động, thời lượng pin 6 giờ");
            product5.setPrice(new BigDecimal("6500000"));
            product5.setStockQuantity(30);
            productRepository.save(product5);

            Product product6 = new Product();
            product6.setName("iPad Pro 12.9 inch");
            product6.setDescription("Tablet Apple với chip M2, màn hình Liquid Retina XDR, hỗ trợ Apple Pencil");
            product6.setPrice(new BigDecimal("32000000"));
            product6.setStockQuantity(12);
            productRepository.save(product6);

            Product product7 = new Product();
            product7.setName("Sony WH-1000XM5");
            product7.setDescription("Tai nghe chống ồn chủ động, pin 30 giờ, Bluetooth 5.2");
            product7.setPrice(new BigDecimal("8500000"));
            product7.setStockQuantity(25);
            productRepository.save(product7);

            Product product8 = new Product();
            product8.setName("Samsung Galaxy Watch 6");
            product8.setDescription("Đồng hồ thông minh, màn hình AMOLED 1.3 inch, chống nước IP68");
            product8.setPrice(new BigDecimal("7500000"));
            product8.setStockQuantity(18);
            productRepository.save(product8);

            Product product9 = new Product();
            product9.setName("Logitech MX Master 3S");
            product9.setDescription("Chuột không dây cao cấp, cảm biến 8000 DPI, pin 70 ngày");
            product9.setPrice(new BigDecimal("3500000"));
            product9.setStockQuantity(40);
            productRepository.save(product9);

            Product product10 = new Product();
            product10.setName("Keychron K8 Pro");
            product10.setDescription("Bàn phím cơ không dây, switch Gateron, layout 87 keys, RGB");
            product10.setPrice(new BigDecimal("4200000"));
            product10.setStockQuantity(22);
            productRepository.save(product10);

            System.out.println("✅ Đã tạo " + productRepository.count() + " sản phẩm mẫu");
        }
    }
}
