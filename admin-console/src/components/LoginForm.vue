<template>
  <div class="login-form">
    <h2>登录</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit" :disabled="isLoading">
        {{ isLoading ? '登录中...' : '登录' }}
      </button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
    <p v-if="successMessage">{{ successMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const username = ref('');
const password = ref('');
const errorMessage = ref('');
const successMessage = ref('');
const isLoading = ref(false);

const handleSubmit = async () => {
  if (!username.value || !password.value) {
    errorMessage.value = '请填写所有字段';
    return;
  }

  try {
    isLoading.value = true;
    errorMessage.value = '';
    successMessage.value = '';

    const response = await axios.post('http://localhost:8080/api/users/login', {
      username: username.value,
      password: password.value
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    // 处理成功响应
    successMessage.value = '登录成功';
    console.log('登录响应:', response.data);

    alert("登录成功")

  } catch (error) {
    // 处理错误
    if (error.response) {
      // 服务器返回了错误状态码
      errorMessage.value = error.response.data.message || '登录失败';
    } else if (error.request) {
      // 请求已发出但没有收到响应
      errorMessage.value = '网络错误，请检查连接';
    } else {
      // 其他错误
      errorMessage.value = '发生未知错误';
    }
    console.error('登录错误:', error);
  } finally {
    isLoading.value = false;
  }
};
</script>