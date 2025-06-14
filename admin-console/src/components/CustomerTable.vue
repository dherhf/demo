<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">顾客管理</h1>

    <!-- 加载状态 -->
    <div v-if="loading" class="py-16 text-center">
      <div
        class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"
      ></div>
      <p class="mt-4 text-lg text-gray-600">正在加载顾客数据...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="py-16 text-center">
      <div class="text-red-500 mb-4">
        <i class="fa fa-exclamation-triangle text-4xl"></i>
      </div>
      <p class="text-xl text-red-600 mb-4">{{ error }}</p>
      <button
        @click="fetchData"
        class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
      >
        重试
      </button>
    </div>

    <!-- 数据表格 -->
    <div v-else class="overflow-x-auto">
      <table class="min-w-full bg-white rounded-lg shadow-md">
        <thead>
          <tr
            class="bg-gray-100 text-gray-600 uppercase text-sm leading-normal"
          >
            <th class="py-3 px-6 text-left">ID</th>
            <th class="py-3 px-6 text-left">姓名</th>
            <th class="py-3 px-6 text-left">地址</th>
            <th class="py-3 px-6 text-left">电话</th>
            <th class="py-3 px-6 text-left">会员卡号</th>
            <th class="py-3 px-6 text-center">订单数</th>
          </tr>
        </thead>
        <tbody class="text-gray-600 text-sm">
          <tr
            v-for="customer in customers"
            :key="customer.id"
            class="border-b border-gray-200 hover:bg-gray-50 transition-colors"
          >
            <td class="py-3 px-6">{{ customer.id }}</td>
            <td class="py-3 px-6 font-medium">{{ customer.name }}</td>
            <td class="py-3 px-6">{{ customer.address }}</td>
            <td class="py-3 px-6">{{ formatPhone(customer.phone) }}</td>
            <td class="py-3 px-6">{{ customer.cardID || "非会员" }}</td>
            <td class="py-3 px-6 text-center">
              <span
                class="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full"
              >
                {{ customer.orderIds.length }}
              </span>
            </td>
          </tr>
          <!-- 编辑态：新增行 -->
          <tr
            v-if="isAdding"
            class="border-b border-dashed border-gray-300 hover:bg-gray-50 transition-colors"
          >
            <td class="py-3 px-6"></td>
            <td class="py-3 px-6">
              <input
                v-model="newCustomer.name"
                class="border border-gray-300 rounded p-1 w-full"
                placeholder="姓名"
                required
              />
            </td>
            <td class="py-3 px-6">
              <input
                v-model="newCustomer.address"
                class="border border-gray-300 rounded p-1 w-full"
                placeholder="地址"
              />
            </td>
            <td class="py-3 px-6">
              <input
                v-model="newCustomer.phone"
                class="border border-gray-300 rounded p-1 w-full"
                placeholder="手机号"
              />
            </td>
            <td class="py-3 px-6">
              <input
                v-model="newCustomer.cardID"
                class="border border-gray-300 rounded p-1 w-full"
                placeholder="会员卡号"
              />
            </td>
            <td class="py-3 px-6"></td>
          </tr>
        </tbody>
      </table>
    </div>

    <div>
      <div
        v-if="!loading"
        class="mt-4 flex justify-between items-center text-sm text-gray-500"
      >
        <span>共 {{ customers.length }} 条记录</span>
        <!-- 操作按钮：添加/保存切换 -->
        <button
          @click="handleAddOrSave"
          class="px-4 py-2 rounded-lg focus:outline-none transition-colors duration-200 flex items-center"
          :class="[
            isAdding
              ? 'bg-green-500 hover:bg-green-600 text-white'
              : 'bg-blue-500 hover:bg-blue-600 text-white',
          ]"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              v-if="!isAdding"
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 4.5v15m7.5-7.5h-15"
            />
            <path
              v-else
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m4.5 12.75 6 6 9-13.5"
            />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

// 定义响应式数据
const customers = ref([]);
// 添加状态标志
const isAdding = ref(false);
// 新顾客临时数据
const newCustomer = ref([]);
const loading = ref(false);
const error = ref(null);

// 格式化手机号
const formatPhone = (phone) => {
  if (!phone) return "未填写";
  // 简单的手机号格式化：11位数字添加空格分隔
  if (/^\d{11}$/.test(phone)) {
    return phone.replace(/(\d{3})(\d{4})(\d{4})/, "$1 $2 $3");
  }
  return phone;
};

// 获取顾客数据
const fetchData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.get(`http://localhost:8080/api/customers`, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    customers.value = response.data;
  } catch (err) {
    console.error("获取顾客数据失败:", err);
    error.value =
      err.response?.data?.message ||
      "无法连接到服务器,请检查API地址和服务器状态";
  } finally {
    loading.value = false;
  }
};

// 处理“添加/保存”按钮逻辑
const handleAddOrSave = async () => {
  if (isAdding.value) {
    // 处于“添加态”，执行保存逻辑
    try {
      // 1. 调用后端接口提交新顾客数据（根据实际接口调整）
      const response = await axios.post(
        `http://localhost:8080/api/customers`,
        newCustomer.value
      );
      // 2. 提交成功后，更新顾客列表
      customers.value.push(response.data);
      // 3. 退出“添加态”
      isAdding.value = false;
      // 4. 清空临时数据
      resetNewCustomer();
    } catch (err) {
      console.error("保存顾客失败:", err);
      error.value = err.response?.data?.message || "保存顾客数据失败,请重试";
    }
  } else {
    // 处于“非添加态”，进入“添加态”
    isAdding.value = true;
  }
};

// 重置新增顾客的临时数据
const resetNewCustomer = () => {
  newCustomer.value = {
    id: 0,
    name: "",
    address: "",
    phone: "",
    cardID: "",
    orderIds: [],
  };
};

// 组件挂载后立即获取数据
onMounted(() => {
  fetchData();
});
</script>
