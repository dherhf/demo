<template>

    <!-- 搜索和筛选区域 -->
    <div class="bg-white rounded-lg shadow-sm p-4 mb-6">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">订单号</label>
          <input
              type="text"
              v-model="searchOrderId"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="输入订单号"
          >
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">日期范围</label>
          <div class="flex items-center space-x-2">
            <input
                type="date"
                v-model="startDate"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
            <span class="text-gray-500">至</span>
            <input
                type="date"
                v-model="endDate"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">状态</label>
          <select
              v-model="statusFilter"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          >
            <option value="">全部状态</option>
            <option value="已下单">已下单</option>
            <option value="制作中">制作中</option>
            <option value="已完成">已完成</option>
            <option value="已取消">已取消</option>
          </select>
        </div>
        <div class="flex items-end">
          <button
              @click="fetchOrders"
              class="w-full px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md shadow-sm text-sm font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
          >
            <i class="fa fa-search mr-1"></i>搜索
          </button>
        </div>
      </div>
    </div>

    <!-- 订单列表 -->
    <div class="bg-white rounded-lg shadow-sm overflow-hidden mb-6">
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
          <tr>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">订单号</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">日期</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">顾客ID</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">桌号</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">总价</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
            <th scope="col" class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
          </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="order in filteredOrders" :key="order.id" class="hover:bg-gray-50 transition-colors">
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">#{{ order.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ order.createDate }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ order.customerId }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ order.deskId }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">¥{{ order.price }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
                <span
                    class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
                    :class="order.status === '已下单' ? 'bg-blue-100 text-blue-800' :
                         order.status === '制作中' ? 'bg-yellow-100 text-yellow-800' :
                         order.status === '已完成' ? 'bg-green-100 text-green-800' :
                         'bg-gray-100 text-gray-800'"
                >
                  {{ order.status }}
                </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
              <button
                  @click="viewOrderDetails(order.id)"
                  class="text-blue-600 hover:text-blue-900 transition-colors"
              >
                查看详情
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

// 订单数据
const orders = ref([])
const loading = ref(true)
const error = ref(null)

// 搜索和筛选条件
const searchOrderId = ref('')
const startDate = ref('')
const endDate = ref('')
const statusFilter = ref('')

// 分页控制
const currentPage = ref(1)
const pageSize = ref(10)

const router = useRouter()

// 获取历史订单
const fetchOrders = async () => {
  loading.value = true
  error.value = null

  try {
    const response = await axios.get('http://localhost:8080/api/order')
    orders.value = response.data || []
  } catch (err) {
    error.value = err.message || '获取订单列表失败'
    console.error('获取订单列表失败:', err)
  } finally {
    loading.value = false
  }
}

// 计算筛选后的订单
const filteredOrders = computed(() => {
  let result = [...orders.value]

  // 按订单号筛选
  if (searchOrderId.value) {
    result = result.filter(order =>
        order.id.toString().includes(searchOrderId.value)
    )
  }

  // 按日期范围筛选
  if (startDate.value && endDate.value) {
    result = result.filter(order => {
      const orderDate = new Date(order.createDate)
      const start = new Date(startDate.value)
      const end = new Date(endDate.value)
      return orderDate >= start && orderDate <= end
    })
  }

  // 按状态筛选
  if (statusFilter.value) {
    result = result.filter(order => order.status === statusFilter.value)
  }

  return result
})

// 查看订单详情
const viewOrderDetails = (orderId) => {
  console.log('点击查看详情，订单ID:', orderId)
  router.push({
    name: 'OrderDetails',
    params: { orderId }
  })
}

onMounted(fetchOrders)
</script>
