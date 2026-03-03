#!/bin/bash
# Launch script cho Mac & Linux - Prompt Course

set -e
cd "$(dirname "$0")"

echo "========================================"
echo "  PROMPT COURSE - KHỞI ĐỘNG DỰ ÁN"
echo "========================================"
echo ""

# Hàm dọn dẹp khi thoát (Ctrl+C)
cleanup() {
  echo ""
  echo "Đang dừng Backend..."
  [ -n "$BACKEND_PID" ] && kill $BACKEND_PID 2>/dev/null || true
  exit 0
}
trap cleanup SIGINT SIGTERM

# 1. Khởi động MySQL (Docker)
echo "[1/3] Đang khởi động MySQL (Docker)..."
cd backend
if command -v docker-compose &>/dev/null; then
  DOCKER_COMPOSE="docker-compose"
else
  DOCKER_COMPOSE="docker compose"
fi
if ! $DOCKER_COMPOSE up -d; then
  echo ""
  echo "[LỖI] Không thể khởi động Docker. Vui lòng cài Docker và thử lại."
  exit 1
fi
cd ..
echo "      MySQL đã chạy tại localhost:3306"
echo ""

# 2. Chạy Backend (Spring Boot) trong nền
echo "[2/3] Đang khởi động Backend (Spring Boot)..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!
cd ..
echo "      Backend sẽ chạy tại http://localhost:8080"
echo ""

# Đợi backend khởi động
echo "Đang đợi Backend khởi động (khoảng 30 giây)..."
sleep 30
echo ""

# 3. Cài đặt và chạy Frontend (Vue.js)
echo "[3/3] Đang khởi động Frontend (Vue.js)..."
cd frontend
[ ! -d "node_modules" ] && echo "      Đang cài đặt dependencies lần đầu..." && npm install
echo "      Frontend sẽ chạy tại http://localhost:5173"
echo ""
echo "========================================"
echo "  ĐÃ KHỞI ĐỘNG: BACKEND + FRONTEND"
echo "  - Backend:  http://localhost:8080"
echo "  - Frontend: http://localhost:5173"
echo "========================================"
echo ""
echo "Nhấn Ctrl+C để dừng cả Backend và Frontend."
echo ""

npm run dev
