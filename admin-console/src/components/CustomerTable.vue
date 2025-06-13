<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-6 text-center">顾客表</h1>

    <!-- 加载状态 -->
    <div v-if="loading" class="py-16 text-center">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"></div>
      <p class="mt-4 text-lg text-gray-600">正在加载顾客数据...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="py-16 text-center">
      <div class="text-red-500 mb-4">
        <i class="fa fa-exclamation-triangle text-4xl"></i>
      </div>
      <p class="text-xl text-red-600 mb-4">{{ error }}</p>
      <button @click="fetchData" class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors">
        <i class="fa fa-refresh mr-2"></i>重试
      </button>
    </div>

    <!-- 数据表格 -->
    <div v-else class="overflow-x-auto">
      <table class="min-w-full bg-white rounded-lg shadow-md">
        <thead>
        <tr class="bg-gray-100 text-gray-600 uppercase text-sm leading-normal">
          <th class="py-3 px-6 text-left">ID</th>
          <th class="py-3 px-6 text-left">姓名</th>
          <th class="py-3 px-6 text-left">地址</th>
          <th class="py-3 px-6 text-left">电话</th>
          <th class="py-3 px-6 text-left">证件号</th>
          <th class="py-3 px-6 text-center">订单数</th>
        </tr>
        </thead>
        <tbody class="text-gray-600 text-sm">
        <tr v-for="customer in customers" :key="customer.id" class="border-b border-gray-200 hover:bg-gray-50 transition-colors">
          <td class="py-3 px-6">{{ customer.id }}</td>
          <td class="py-3 px-6 font-medium">{{ customer.name }}</td>
          <td class="py-3 px-6">{{ customer.address }}</td>
          <td class="py-3 px-6">{{ formatPhone(customer.phone) }}</td>
          <td class="py-3 px-6">{{ customer.cardID || '未填写' }}</td>
          <td class="py-3 px-6 text-center">
              <span class="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full">
                {{ customer.orderIds.length }}
              </span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 表格下方的统计信息 -->
    <div v-if="customers.length > 0" class="mt-4 text-sm text-gray-500">
      共 {{ customers.length }} 条记录
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

// 定义响应式数据
const customers = ref([]);
const loading = ref(false);
const error = ref(null);

// API配置
const API_BASE_URL = 'http://localhost:8080/api';

// 格式化手机号
const formatPhone = (phone) => {
  if (!phone) return '未填写';
  // 简单的手机号格式化：11位数字添加空格分隔
  if (/^\d{11}$/.test(phone)) {
    return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1 $2 $3');
  }
  return phone;
};

// 获取顾客数据
const fetchData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.get(`${API_BASE_URL}/customers`, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log(customers)
    customers.value = response.data;
  } catch (err) {
    console.error('获取顾客数据失败:', err);
    error.value = err.response?.data?.message || '无法连接到服务器，请检查API地址和服务器状态';
  } finally {
    loading.value = false;
  }
};

// 组件挂载后立即获取数据
onMounted(() => {
  fetchData();
});
</script>

<style scoped>
/* 为表格添加淡入动画 */
tr {
  animation: fadeIn 0.5s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>