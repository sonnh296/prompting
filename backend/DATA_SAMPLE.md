# Data Mẫu Tự Động

## Cách hoạt động

Data mẫu được tự động tạo bởi `DataInitializer` class khi Spring Boot khởi động. Class này implement `CommandLineRunner` và sẽ chạy sau khi application context đã được khởi tạo hoàn toàn.

## Users mặc định

Khi database trống, hệ thống sẽ tự động tạo 3 users để test:

| Username | Password | Email |
|----------|----------|-------|
| admin | admin123 | admin@example.com |
| testuser | password123 | test@example.com |
| demo | demo123 | demo@example.com |

## Sản phẩm mẫu

10 sản phẩm sẽ được tự động tạo với đầy đủ thông tin (tên, mô tả, giá, số lượng tồn kho).

## Điều kiện insert

- **Users**: Chỉ insert khi `userRepository.count() == 0`
- **Products**: Chỉ insert khi `productRepository.count() == 0`

Điều này đảm bảo:
- Data chỉ được tạo lần đầu tiên
- Không bị duplicate khi restart application
- An toàn khi có data sẵn có

## Reset data

Nếu muốn reset và tạo lại data mẫu:

1. **Xóa volume Docker (xóa toàn bộ database):**
```bash
cd backend
docker-compose down -v
docker-compose up -d
```

2. **Hoặc xóa thủ công trong database:**
```sql
DELETE FROM refresh_tokens;
DELETE FROM products;
DELETE FROM users;
```

Sau đó restart backend, data sẽ được tạo lại tự động.

## Logs

Khi data được tạo thành công, bạn sẽ thấy log trong console:
```
✅ Đã tạo 3 user mặc định
   - admin / admin123
   - testuser / password123
   - demo / demo123
✅ Đã tạo 10 sản phẩm mẫu
```
