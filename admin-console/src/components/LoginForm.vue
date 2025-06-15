<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div
        class="max-w-md w-full space-y-8 bg-white rounded-lg shadow-xl p-8 transform transition-all duration-300 hover:shadow-2xl">
      <div class="flex flex-col items-center">
        <h2 class="mt-6 text-center text-3xl font-bold text-gray-900">登录到您的账户</h2>
        <p class="mt-2 text-sm text-gray-500 max-w">
          或 <a href="#" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">创建新账户</a>
        </p>
      </div>

      <form @submit.prevent="handleSubmit" class="mt-8 space-y-6">
        <input type="hidden" name="remember" value="true">
        <div class="rounded-md shadow-sm -space-y-px ">


          <div class="relative">
            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
              </svg>
            </div>
            <input type="text" id="username" v-model="username" required
                   class="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm transition-all duration-300"
                   placeholder="请输入用户名">
          </div>

          <div class="mb-4"/>


          <div class="relative">
            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z"/>
              </svg>
            </div>
            <input type="password" id="password" v-model="password" required
                   class="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm transition-all duration-300"
                   placeholder="请输入密码">
          </div>

        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input id="remember-me" name="remember-me" type="checkbox"
                   class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded">
            <label for="remember-me" class="ml-2 block text-sm text-gray-900">记住我</label>
          </div>

          <div class="text-sm">
            <a href="#" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">忘记密码?</a>
          </div>
        </div>

        <div>
          <button type="submit" :disabled="isLoading"
                  class="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-all duration-300 transform hover:scale-[1.02] active:scale-[0.98]">
            <span v-if="isLoading" class="animate-spin mr-2">
              <i class="fa fa-circle-o-notch"></i>
            </span>
            {{ isLoading ? "登录中..." : "登录" }}
          </button>
        </div>
      </form>


      <div class="mt-6">
        <p v-if="errorMessage"
           class="text-sm text-red-600 bg-red-50 border border-red-400 rounded-lg p-3 flex items-center">
          <i class="fa fa-exclamation-circle mr-2"></i>
          {{ errorMessage }}
        </p>
        <p v-if="successMessage"
           class="text-sm text-green-600 bg-green-50 border border-green-400 rounded-lg p-3 flex items-center">
          <i class="fa fa-check-circle mr-2"></i>
          {{ successMessage }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import { useRouter } from 'vue-router'
import axios from "axios";

const router = useRouter()
const username = ref("");
const password = ref("");
const errorMessage = ref("");
const successMessage = ref("");
const isLoading = ref(false);

const handleSubmit = async () => {
  if (!username.value || !password.value) {
    errorMessage.value = "请填写所有字段";
    return;
  }

  try {
    isLoading.value = true;
    errorMessage.value = "";
    successMessage.value = "";
    await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          username: username.value,
          password: password.value,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
    );
// 处理成功响应
    successMessage.value = "登录成功";
    await router.push('/dashboard')
  } catch (error) {
    // 处理错误
    if (error.response) {
      // 服务器返回了错误状态码
      errorMessage.value = error.response.data.message || "登录失败";
    } else if (error.request) {
      // 请求已发出但没有收到响应
      errorMessage.value = "网络错误，请检查连接";
    } else {
      // 其他错误
      errorMessage.value = "发生未知错误";
    }
  } finally {
    isLoading.value = false;
  }
};
</script>
