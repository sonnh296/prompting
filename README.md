# Prompt Course - Full Stack Application

Dự án này được tạo để dạy khoá học prompt, bao gồm backend Spring Boot và frontend Vue.js.

## Cấu trúc dự án

```
prompt_dev/
├── backend/          # Spring Boot Backend
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── docker-compose.yml
└── frontend/         # Vue.js Frontend (trống để thực hành)
    ├── src/
    ├── package.json
    └── vite.config.js
```

## Backend (Spring Boot)

Backend đã được cấu hình đầy đủ với:
- ✅ Đăng ký người dùng (`POST /api/auth/register`)
- ✅ Đăng nhập người dùng (`POST /api/auth/login`)
- ✅ Refresh token (`POST /api/auth/refresh`)
- ✅ Logout (`POST /api/auth/logout`)
- ✅ JWT Access Token và Refresh Token
- ✅ Bảng sản phẩm cơ bản (`GET /api/products`)

### Cài đặt và chạy Backend

1. **Khởi động MySQL bằng Docker:**
```bash
cd backend
docker-compose up -d
```

   MySQL sẽ tự động chạy script init trong `docker/init.sql` khi container khởi động lần đầu.

2. **Chạy Backend:**
```bash
cd backend
mvn spring-boot:run
```

Backend sẽ chạy tại: `http://localhost:8080`

### API Endpoints

#### Authentication
- `POST /api/auth/register` - Đăng ký người dùng mới
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/refresh` - Làm mới access token
- `POST /api/auth/logout` - Đăng xuất

#### Products
- `GET /api/products` - Lấy danh sách sản phẩm
- `GET /api/products/{id}` - Lấy thông tin sản phẩm theo ID

### Ví dụ Request/Response

**Đăng ký:**
```json
POST /api/auth/register
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123"
}

Response:
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "uuid-token",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "testuser"
}
```

**Đăng nhập (với user mặc định):**
```json
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}

Response: (tương tự như đăng ký)
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "uuid-token",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "admin"
}
```

**Refresh Token:**
```json
POST /api/auth/refresh
{
  "refreshToken": "uuid-token"
}

Response: (trả về access token và refresh token mới)
```

## Frontend (Vue.js)

Frontend được tạo trống để học viên thực hành:
- Tạo form đăng nhập/đăng ký
- Lưu trữ access token và refresh token
- Sử dụng token để gọi API
- Xử lý refresh token khi access token hết hạn
- Quản lý state authentication

### Cài đặt và chạy Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend sẽ chạy tại: `http://localhost:5173`

## Database

MySQL được chạy trong Docker với:
- Database: `prompt_course_db`
- Username: `root`
- Password: `rootpassword`
- Port: `3306`

### Bảng dữ liệu

1. **users** - Thông tin người dùng
2. **products** - Danh sách sản phẩm
3. **refresh_tokens** - Refresh tokens của người dùng

### Data mẫu tự động

Khi khởi động backend lần đầu, hệ thống sẽ tự động tạo:

#### Users mặc định (để test):
- **admin** / **admin123** (email: admin@example.com)
- **testuser** / **password123** (email: test@example.com)
- **demo** / **demo123** (email: demo@example.com)

#### 10 Sản phẩm mẫu:
1. Laptop Dell XPS 15 - 25,000,000 VNĐ
2. iPhone 15 Pro - 28,000,000 VNĐ
3. Samsung Galaxy S24 - 22,000,000 VNĐ
4. MacBook Pro M3 - 45,000,000 VNĐ
5. AirPods Pro 2 - 6,500,000 VNĐ
6. iPad Pro 12.9 inch - 32,000,000 VNĐ
7. Sony WH-1000XM5 - 8,500,000 VNĐ
8. Samsung Galaxy Watch 6 - 7,500,000 VNĐ
9. Logitech MX Master 3S - 3,500,000 VNĐ
10. Keychron K8 Pro - 4,200,000 VNĐ

**Lưu ý:** Data chỉ được tạo tự động khi database trống (lần đầu chạy). Nếu muốn reset data, xóa volume Docker:
```bash
docker-compose down -v
docker-compose up -d
```

## Nhiệm vụ cho học viên

### Frontend:
1. Tạo trang đăng nhập (`/login`)
2. Tạo trang đăng ký (`/register`)
3. Tạo service để gọi API authentication
4. Lưu trữ access token và refresh token (localStorage/sessionStorage)
5. Tạo interceptor để tự động thêm token vào header
6. Xử lý refresh token khi access token hết hạn
7. Tạo trang hiển thị danh sách sản phẩm (có authentication)
8. Tạo trang profile người dùng (có authentication)

## Lưu ý

- Access token có thời hạn: 15 phút (900000ms)
- Refresh token có thời hạn: 24 giờ (86400000ms)
- JWT secret key nên được thay đổi trong production
- CORS đã được cấu hình cho `http://localhost:5173` và `http://localhost:3000`
