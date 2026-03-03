@echo off
chcp 65001 >nul
title Prompt Course - Launcher
cd /d "%~dp0"

echo ========================================
echo   PROMPT COURSE - KHỞI ĐỘNG DỰ ÁN
echo ========================================
echo.

:: 1. Khởi động MySQL (Docker)
echo [1/3] Đang khởi động MySQL (Docker)...
cd backend
docker-compose up -d
if errorlevel 1 (
    echo.
    echo [LỖI] Không thể khởi động Docker. Vui lòng cài Docker Desktop và thử lại.
    pause
    exit /b 1
)
cd ..
echo       MySQL đã chạy tại localhost:3306
echo.

:: 2. Chạy Backend (Spring Boot) trong cửa sổ mới
echo [2/3] Đang mở Backend (Spring Boot)...
start "Backend - Spring Boot" cmd /k "cd /d ""%~dp0backend"" && echo Đang khởi động Backend... && mvn spring-boot:run"
echo       Backend sẽ chạy tại http://localhost:8080
echo.

:: Đợi backend khởi động
echo Đang đợi Backend khởi động (khoảng 30 giây)...
ping 127.0.0.1 -n 31 >nul
echo.

:: 3. Cài đặt và chạy Frontend (Vue.js) trong cửa sổ mới
echo [3/3] Đang mở Frontend (Vue.js)...
if not exist "frontend\node_modules" (
    echo       Đang cài đặt dependencies lần đầu...
)
start "Frontend - Vue.js" cmd /k "cd /d ""%~dp0frontend"" && (if not exist node_modules npm install) && npm run dev"
echo       Frontend sẽ chạy tại http://localhost:5173
echo.
echo ========================================
echo   ĐÃ MỞ 2 CỬA SỔ: BACKEND + FRONTEND
echo   - Backend:  http://localhost:8080
echo   - Frontend: http://localhost:5173
echo ========================================
echo.
echo Bạn có thể đóng cửa sổ này. Để dừng: đóng 2 cửa sổ Backend và Frontend.
pause
