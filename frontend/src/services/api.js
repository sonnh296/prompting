import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

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
