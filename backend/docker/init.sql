-- Script khởi tạo database và data mẫu
-- Script này sẽ tự động chạy khi MySQL container khởi động lần đầu

USE prompt_course_db;

-- Tạo bảng users nếu chưa có (sẽ được tạo bởi JPA, nhưng đây là backup)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME
);

-- Tạo bảng products nếu chưa có
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    stock_quantity INT,
    created_at DATETIME
);

-- Tạo bảng refresh_tokens nếu chưa có
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Lưu ý: Users và Products sẽ được tạo bởi Spring Boot DataInitializer
-- với password đã được mã hóa bằng BCrypt
-- Script này chỉ tạo cấu trúc bảng như backup
