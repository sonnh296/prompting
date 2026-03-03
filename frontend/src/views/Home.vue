<template>
  <div class="home">
    <h2>Trang chủ</h2>
    <p>Đây là trang chủ trống. Bạn cần tạo các trang đăng nhập, đăng ký và quản lý token ở đây.</p>
    
    <div v-if="loading" class="loading">
      <p>Đang tải danh sách sản phẩm...</p>
    </div>
    
    <div v-if="error" class="error">
      <p><strong>Lỗi:</strong> {{ error }}</p>
      <p v-if="errorDetails">{{ errorDetails }}</p>
    </div>
    
    <div v-if="products && products.length > 0" class="products">
      <h3>Danh sách sản phẩm:</h3>
      <ul>
        <li v-for="product in products" :key="product.id">
          {{ product.name }} - {{ formatPrice(product.price) }} VNĐ
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { productService } from '../services/api'

export default {
  name: 'Home',
  data() {
    return {
      products: [],
      loading: false,
      error: null,
      errorDetails: null
    }
  },
  mounted() {
    this.fetchProducts()
  },
  methods: {
    async fetchProducts() {
      this.loading = true
      this.error = null
      this.errorDetails = null
      
      try {
        const response = await productService.getAllProducts()
        this.products = response.data
        this.loading = false
      } catch (err) {
        this.loading = false
        if (err.response) {
          // Server responded with error status
          this.error = `Lỗi ${err.response.status}: ${err.response.statusText}`
          if (err.response.status === 401 || err.response.status === 403) {
            this.errorDetails = 'Bạn chưa đăng nhập hoặc token không hợp lệ. Vui lòng đăng nhập để xem danh sách sản phẩm.'
          } else {
            this.errorDetails = err.response.data?.message || 'Không thể tải danh sách sản phẩm'
          }
        } else if (err.request) {
          // Request was made but no response received
          this.error = 'Không thể kết nối đến server'
          this.errorDetails = 'Vui lòng kiểm tra xem backend đã chạy chưa'
        } else {
          // Something else happened
          this.error = 'Đã xảy ra lỗi'
          this.errorDetails = err.message
        }
        console.error('Error fetching products:', err)
      }
    },
    formatPrice(price) {
      return new Intl.NumberFormat('vi-VN').format(price)
    }
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
}
</style>
