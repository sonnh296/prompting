import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL
    ? `${import.meta.env.VITE_API_BASE_URL}/api`
    : 'http://localhost:8080/api',
  timeout: 15000, // 15s - tránh treo "pending" khi backend tắt hoặc chậm
  headers: {
    'Content-Type': 'application/json'
  }
})

// Gợi ý khi lỗi: backend chưa chạy / timeout / cần đăng nhập
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.code === 'ECONNABORTED') {
      err.message = 'Backend không phản hồi. Kiểm tra backend đã chạy tại http://localhost:8080 chưa.'
    } else if (err.response?.status === 401) {
      err.message = 'Cần đăng nhập để xem sản phẩm.'
    } else if (err.code === 'ERR_NETWORK') {
      err.message = 'Không kết nối được backend (CORS hoặc backend chưa chạy).'
    }
    return Promise.reject(err)
  }
)

// Product API
export const productService = {
  getAllProducts: () => {
    return api.get('/products')
  },
  
  getProductById: (id) => {
    return api.get(`/products/${id}`)
  }
}

export default api
