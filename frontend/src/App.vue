<template>
  <div id="app">
    <h1>Prompt Course - Frontend Practice</h1>
    <p>Đây là frontend trống để bạn thực hành tạo chức năng đăng nhập và sử dụng access token + refresh token.</p>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
