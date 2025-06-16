<template>
  <div class="flex h-full bg-gray-50 overflow-hidden">
    <!-- 分类栏 -->
    <div class="w-64 bg-white shadow-lg p-4 overflow-y-auto">
      <h2 class="text-xl font-bold mb-4">菜品分类</h2>
      <ul class="space-y-2">
        <li
            v-for="category in dishCategory"
            :key="category.id"
            :class="category.id === currentCategoryId ? 'bg-blue-100 font-semibold' : 'hover:bg-gray-100'"
            @click="currentCategoryId = category.id"
            class="cursor-pointer px-4 py-2 rounded"
        >
          {{ category.tags }}
        </li>
      </ul>
    </div>

    <!-- 菜品 + 点单区域 -->
    <div class="flex-1 flex flex-col p-6 space-y-6 overflow-y-auto">
      <Order ref="orderComponent"/>
      <!-- 菜品展示 -->
      <div>
        <h2 class="text-2xl font-semibold mb-4">菜品列表</h2>
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          <div
              v-for="dish in filteredDishes"
              :key="dish.id"
              class="bg-white rounded-lg shadow p-4 hover:shadow-lg transition"
          >
            <h3 class="text-lg font-bold text-gray-800 mb-2">{{ dish.name }}</h3>
            <p class="text-sm text-gray-500 mb-1">价格：¥{{ dish.price }}</p>
            <p class="text-xs text-gray-400 mb-3">{{ dish.description }}</p>
            <button
                @click="addToOrder(dish)"
                class="px-3 py-1 bg-green-600 text-white rounded hover:bg-green-700 text-sm"
            >
              加入点单
            </button>
          </div>
        </div>
      </div>

      <!-- 点单栏 -->
      <div class="bg-white shadow rounded p-4">
        <h2 class="text-xl font-semibold mb-4">已点菜品</h2>
        <div v-if="orderItem.length === 0" class="text-gray-500">暂无菜品，请选择菜品</div>
        <div v-else>
          <table class="w-full">
            <thead class="bg-gray-50">
            <tr class="text-left text-gray-600">
              <th class="py-4 px-6 font-medium">菜品</th>
              <th class="py-4 px-6 font-medium">数量</th>
              <th class="py-4 px-6 font-medium">单价</th>
              <th class="py-4 px-6 font-medium">小计</th>
              <th class="py-4 px-6 font-medium">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in orderItem" :key="item.id"
                class="border-b border-gray-100 hover:bg-gray-50 transition-colors duration-200">
              <td class="py-4 px-6 font-medium">{{ item.name }}</td>
              <td class="py-4 px-6">
                <input type="number" v-model.number="item.quantity" min="1"
                       class="w-20 border border-gray-300 rounded-md text-center px-2 py-1 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"/>
              </td>
              <td class="py-4 px-6 text-gray-700">¥{{ item.price }}</td>
              <td class="py-4 px-6 font-medium text-gray-800">¥{{ item.price * item.quantity }}</td>
              <td class="py-4 px-6">
                <button @click="removeFromOrder(item.id)"
                        class="text-red-500 hover:text-red-700 hover:underline text-sm transition-colors">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                       stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"/>
                  </svg>
                </button>
              </td>
            </tr>
            </tbody>
          </table>

          <div class="flex justify-between items-center">
            <div class="text-lg font-semibold">总计：¥{{ totalAmount }}</div>
            <button
                @click="submitOrder"
                class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              确认下单
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue'
import axios from "axios";
import Order from "@/components/Order.vue";

// 创建组件引用
const orderComponent = ref(null);

const loadingDishCategory = ref(false)
const loadingDish = ref(false)
const error = ref(null)
const dishCategory = ref([])

const fetchDishCategoryData = async () => {
  loadingDishCategory.value = true
  error.value = null
  try {
    const response = await axios.get("http://localhost:8080/api/dish-category", {
      headers: {
        "Content-Type": "application/json",
      },
    });
    dishCategory.value = response.data || [];
  } catch (err) {
    error.value = err.message || '获取分类数据失败'
    console.error('获取分类数据失败:', err)
  } finally {
    loadingDishCategory.value = false
  }
}

const dishes = ref([])

const fetchDishData = async () => {
  loadingDish.value = true
  error.value = null
  try {
    const response = await axios.get("http://localhost:8080/api/dish", {
      headers: {
        "Content-Type": "application/json",
      },
    });
    dishes.value = response.data
  } catch (err) {
    error.value = err.message || '获取菜品数据失败'
    console.error('获取菜品数据失败:', err)
  } finally {
    loadingDish.value = false
  }
}

const currentCategoryId = ref(null)

const filteredDishes = computed(() =>
    dishes.value.filter(dish => dish.categoryId === currentCategoryId.value)
)

const orderItem = ref([])

const addToOrder = (dish) => {
  const existing = orderItem.value.find(item => item.id === dish.id)
  if (existing) {
    existing.quantity += 1
  } else {
    orderItem.value.push({...dish, quantity: 1})
  }
}

const removeFromOrder = (id) => {
  orderItem.value = orderItem.value.filter(item => item.id !== id)
}

const totalAmount = computed(() =>
    orderItem.value.reduce((sum, item) => sum + item.quantity * item.price, 0)
)

const submitOrder = async () => {
  try {
    // 获取当前选中的桌台、客户和员工信息
    // 通过组件引用获取数据
    const { desk, customer, employee } = orderComponent.value?.getSelectedData() || {};

    // 验证必要信息
    if (!desk || !customer || !employee) {
      alert('请先选择桌台、客户和服务员！');
      return;
    }

    // 构建订单主数据
    const orderMainData = {
      createDate: new Date().toISOString(),
      price: totalAmount.value, // 假设price为对象结构
      status: "已下单",
      customerId: customer.id, // 示例默认值，实际需从用户系统获取
      deskId: desk.id,     // 示例默认值，实际需获取桌号
      employeeId: employee.id  // 示例默认值，实际需获取服务员ID
    };

    // 2. 提交订单主数据
    const orderResponse = await axios.post(
        "http://localhost:8080/api/order",
        orderMainData,
        {headers: {"Content-Type": "application/json"}}
    );

    const orderId = orderResponse.data.id

    // 4. 批量提交订单项（假设后端支持批量接口，若不支持则需逐个提交）
    for (const item of orderItem.value) {
      const orderItemData = {
        quantity: item.quantity,
        dishId: item.id,
        orderId: orderId
      };

      await axios.post(
          "http://localhost:8080/api/order-item",
          orderItemData,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
      );
    }

    // 5. 处理成功响应
    orderItem.value = []; // 清空购物车
    alert(`订单提交成功！订单号：${orderId}`);

    // 可添加跳转到订单详情页的逻辑
    // router.push(`/order/${orderId}`);

  } catch (error) {
    console.error("订单提交失败", error);
    alert(
        error.response?.data?.message ||
        error.message ||
        "订单提交失败，请检查网络连接或稍后再试"
    );
  }
}

onMounted(fetchDishCategoryData)
onMounted(fetchDishData)
</script>
