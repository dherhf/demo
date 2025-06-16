<template>


    <!-- 订单详情卡片 -->
    <div class="bg-white rounded-xl shadow-md overflow-hidden mb-6">
      <div class="p-6 border-b border-gray-200">
        <h2 class="text-xl font-bold text-gray-900">订单 #{{ order.id }}</h2>
        <div class="flex flex-wrap items-center mt-2 text-sm text-gray-600">
          <div class="mr-4"><i class="fa fa-calendar-o mr-1"></i> {{ order.createDate }}</div>
          <div class="mr-4"><i class="fa fa-user-o mr-1"></i> 顾客ID: {{ order.customerId }}</div>
          <div class="mr-4"><i class="fa fa-cutlery mr-1"></i> 桌号: {{ order.deskId }}</div>
          <div class="mr-4"><i class="fa fa-user mr-1"></i> 服务员: {{ order.employeeId }}</div>
          <div class="px-3 py-1 rounded-full text-xs font-medium"
               :class="order.status === '已下单' ? 'bg-blue-100 text-blue-800' :
                      order.status === '制作中' ? 'bg-yellow-100 text-yellow-800' :
                      order.status === '已完成' ? 'bg-green-100 text-green-800' :
                      'bg-gray-100 text-gray-800'">
            {{ order.status }}
          </div>
        </div>
      </div>

      <!-- 订单商品列表 -->
      <div class="p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">订单商品</h3>
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">商品</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">单价</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">数量</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">小计</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="item in orderItems" :key="item.id" class="hover:bg-gray-50 transition-colors">
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10 bg-gray-200 rounded-md overflow-hidden">
                    <!-- 图片占位 -->
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">{{ getDishName(item.dishId) || '未知菜品' }}</div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">¥{{ getDishPrice(item.dishId) || 0 }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ item.quantity }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">¥{{ (getDishPrice(item.dishId) || 0) * item.quantity }}</td>
            </tr>
            </tbody>
          </table>
        </div>

        <!-- 订单总计 -->
        <div class="mt-6 flex justify-end">
          <div class="w-full max-w-md">
            <div class="border-t border-gray-200 py-4">
              <div class="flex justify-between items-center">
                <span class="text-sm text-gray-500">总计</span>
                <span class="text-lg font-bold text-gray-900">¥{{ order.price }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 订单操作按钮 -->
    <div class="flex justify-end space-x-4 mb-6">
      <button
          v-if="order.status === '已下单'"
          @click="updateOrderStatus('制作中')"
          class="px-4 py-2 border border-yellow-500 text-yellow-700 rounded-md shadow-sm text-sm font-medium bg-white hover:bg-yellow-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500 transition-colors"
      >
        <i class="fa fa-cogs mr-1"></i>开始制作
      </button>
      <button
          v-if="order.status === '制作中'"
          @click="updateOrderStatus('已完成')"
          class="px-4 py-2 border border-green-500 text-green-700 rounded-md shadow-sm text-sm font-medium bg-white hover:bg-green-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 transition-colors"
      >
        <i class="fa fa-check mr-1"></i>标记完成
      </button>
      <button
          @click="updateOrderStatus('已取消')"
          class="px-4 py-2 border border-red-500 text-red-700 rounded-md shadow-sm text-sm font-medium bg-white hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-colors"
      >
        <i class="fa fa-times mr-1"></i>取消订单
      </button>
      <!-- 返回按钮 -->
        <button
            @click="goBack"
            class="px-4 py-2 border rounded-md shadow-sm text-sm font-medium bg-white focus:outline-none focus:ring-2 focus:ring-offset-2 "
        >
          <i class="fa fa-arrow-left mr-2"></i>返回订单列表
        </button>
    </div>

</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
const route = useRoute()
// 订单id
const orderId = route.params.orderId
// 订单数据
const order = ref({})
const orderItems = ref([])
const dishes = ref([])
const loading = ref(true)
const error = ref(null)

// 获取订单详情
const fetchOrderDetails = async () => {
  loading.value = true
  error.value = null

  try {
    const orderId = route.params.orderId
    // 获取订单信息
    const orderResponse = await axios.get(`http://localhost:8080/api/order/${orderId}`)
    order.value = orderResponse.data

    // 获取订单菜品信息
    const orderItemsResponse = await axios.get(`http://localhost:8080/api/order-item/order/${orderId}`)
    orderItems.value = orderItemsResponse.data

    // 从orderItems中提取所有dishId
    const dishIds = orderItems.value.map(item => item.dishId)

    // 并行请求所有菜品信息
    const dishRequests = dishIds.map(id =>
        axios.get(`http://localhost:8080/api/dish/${id}`)
    )

    const dishResponses = await Promise.all(dishRequests)
    dishes.value = dishResponses.reduce((acc, response) => {
      const dish = response.data
      acc[dish.id] = dish
      return acc
    }, {})

  } catch (err) {
    error.value = err.message || '获取订单详情失败'
    console.error('获取订单详情失败:', err)
  } finally {
    loading.value = false
  }
}

const getDishName = (dishId) => {
  return dishes.value[dishId]?.name || '未知菜品'
}

const getDishPrice = (dishId) => {
  return dishes.value[dishId]?.price || 0
}

// 更新订单状态
const updateOrderStatus = async (newStatus) => {
  try {
    await axios.put(`http://localhost:8080/api/order/${orderId}`, {
      ...order.value,
      status: newStatus
    })

    order.value.status = newStatus
    alert(`订单状态已更新为: ${newStatus}`)
  } catch (err) {
    error.value = err.message || '更新订单状态失败'
    console.error('更新订单状态失败:', err)
    alert('更新订单状态失败，请重试')
  }
}

// 返回上一页
const goBack = () => {
  window.history.back()
}

// 监听订单ID变化，重新获取数据
watch(() => orderId, () => {
  fetchOrderDetails()
})

onMounted(fetchOrderDetails)
</script>
