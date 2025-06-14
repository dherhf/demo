<template>
  <div class="flex h-screen bg-gray-50">
    <!-- 左侧：菜品分类列表 -->
    <div class="w-64 bg-white shadow-lg p-4">
      <h3 class="text-xl font-semibold text-gray-800 mb-4">菜品分类</h3>
      <ul class="space-y-2">
        <li
            v-for="category in categories"
            :key="category.id"
            :class="currentCategoryId === category.id ? 'bg-blue-50 border-l-4 border-blue-500' : 'hover:bg-gray-100'"
            @click="selectCategory(category.id)"
            class="cursor-pointer p-3 rounded transition duration-200"
        >
          {{ category.name }}
        </li>
      </ul>
    </div>

    <!-- 右侧：菜品内容区域 -->
    <div class="flex-1 p-6 overflow-auto">
      <!-- 单行表格：当前分类信息 -->
      <div class="mb-8 bg-white rounded-lg shadow-md overflow-hidden">
        <table class="w-full">
          <thead>
          <tr class="bg-gray-100">
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">分类ID</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">分类名称</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">描述</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="currentCategory">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ currentCategory.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ currentCategory.name }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ currentCategory.description }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 主要区域：菜品表格 -->
        <div class="overflow-x-auto bg-white rounded-lg shadow-md overflow-hidden">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">菜品ID</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">菜品名称</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">价格</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">描述</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="food in filteredFoods" :key="food.id" class="hover:bg-gray-50 transition duration-150">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ food.id }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ food.name }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">¥{{ food.price }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ food.description }}</td>
            </tr>
            </tbody>
          </table>
        </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 示例数据：菜品分类
const categories = ref([
  { id: 1, name: '川菜', description: '麻辣鲜香的四川菜系' },
  { id: 2, name: '粤菜', description: '清淡鲜美的广东菜系' },
  { id: 3, name: '鲁菜', description: '讲究火候的山东菜系' },
  { id: 4, name: '苏菜', description: '造型精致的江苏菜系' },
  { id: 5, name: '浙菜', description: '清鲜脆嫩的浙江菜系' }
])

// 示例数据：菜品
const foods = ref([
  { id: 101, categoryId: 1, name: '麻婆豆腐', price: 28, description: '麻辣鲜香的豆腐料理' },
  { id: 102, categoryId: 1, name: '宫保鸡丁', price: 36, description: '鸡肉与花生的经典搭配' },
  { id: 201, categoryId: 2, name: '白切鸡', price: 68, description: '保持原汁原味的鸡料理' },
  { id: 202, categoryId: 2, name: '叉烧肉', price: 42, description: '甜香可口的猪肉料理' },
  { id: 301, categoryId: 3, name: '糖醋鲤鱼', price: 88, description: '外酥里嫩的传统名菜' },
  { id: 302, categoryId: 3, name: '葱烧海参', price: 128, description: '高档的海鲜料理' },
  { id: 401, categoryId: 4, name: '松鼠鳜鱼', price: 108, description: '造型独特的鱼类料理' },
  { id: 402, categoryId: 4, name: '清炖狮子头', price: 58, description: '入口即化的肉丸料理' },
  { id: 501, categoryId: 5, name: '西湖醋鱼', price: 78, description: '杭州传统名菜' },
  { id: 502, categoryId: 5, name: '东坡肉', price: 48, description: '肥而不腻的猪肉料理' }
])

// 当前选中的分类ID
const currentCategoryId = ref(null)

// 当前选中的分类对象
const currentCategory = computed(() =>
    categories.value.find(category => category.id === currentCategoryId.value)
)

// 根据当前分类过滤的菜品列表
const filteredFoods = computed(() =>
    currentCategory.value
        ? foods.value.filter(food => food.categoryId === currentCategory.value.id)
        : []
)

// 选择分类的方法
const selectCategory = (id) => {
  currentCategoryId.value = id
}
</script>