<template>
  <div class="container mx-auto p-4">
    <!-- 搜索和添加区域 -->
    <div class="flex flex-col md:flex-row gap-4 mb-6">
      <div class="relative flex-grow">
        <input
            type="text"
            v-model="searchTerm"
            placeholder="搜索餐台..."
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
        >
        <button
            @click="searchTerm = ''"
            v-show="searchTerm"
            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
        >
          <i class="fa fa-times-circle"></i>
        </button>
      </div>
      <button
          @click="openAddModal"
          class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition-colors flex items-center justify-center"
      >
        <i class="fa fa-plus mr-2"></i>添加餐台
      </button>
    </div>

    <!-- 餐台列表 -->
    <div class="overflow-x-auto">
      <table class="min-w-full bg-white rounded-lg overflow-hidden">
        <thead>
        <tr class="bg-gray-100 text-left">
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">ID</th>
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">餐台编号</th>
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">描述</th>
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">容量</th>
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">状态</th>
          <th class="px-6 py-3 text-sm font-semibold text-gray-600">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="desk in filteredDesks" :key="desk.id" class="border-b border-gray-200 hover:bg-gray-50 transition-colors">
          <td class="px-6 py-4 text-sm text-gray-800">{{ desk.id }}</td>
          <td class="px-6 py-4 text-sm text-gray-800">{{ desk.code }}</td>
          <td class="px-6 py-4 text-sm text-gray-800">{{ desk.description }}</td>
          <td class="px-6 py-4 text-sm text-gray-800">{{ desk.capacity }}</td>
          <td class="px-6 py-4 text-sm">
              <span
                  :class="desk.open ? 'bg-green-100 text-green-800 px-2 py-1 rounded-full text-xs font-medium' : 'bg-red-100 text-red-800 px-2 py-1 rounded-full text-xs font-medium'"
              >
                {{ desk.open ? '开放' : '关闭' }}
              </span>
          </td>
          <td class="px-6 py-4 text-sm">
            <button
                @click="openEditModal(desk)"
                class="text-blue-500 hover:text-blue-700 mr-3 transition-colors"
            >
              <i class="fa fa-edit"></i> 编辑
            </button>
            <button
                @click="deleteDesk(desk.id)"
                class="text-red-500 hover:text-red-700 transition-colors"
            >
              <i class="fa fa-trash"></i> 删除
            </button>
          </td>
        </tr>
        <tr v-if="!desks.length">
          <td colspan="6" class="px-6 py-4 text-center text-gray-500">暂无餐台数据</td>
        </tr>
        <tr v-else-if="!filteredDesks.length">
          <td colspan="6" class="px-6 py-4 text-center text-gray-500">未找到匹配的餐台</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑餐台模态框 -->
    <div v-if="modalVisible" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg shadow-xl w-full max-w-md mx-4 transform transition-all duration-300 scale-100">
        <div class="p-6">
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-bold text-gray-800">{{ isEditing ? '编辑餐台' : '添加餐台' }}</h3>
            <button @click="closeModal" class="text-gray-400 hover:text-gray-600">
              <i class="fa fa-times"></i>
            </button>
          </div>

          <form @submit.prevent="submitForm">
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1" for="code">餐台编号</label>
              <input
                  type="text"
                  id="code"
                  v-model="formData.code"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                  required
              >
            </div>

            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1" for="description">描述</label>
              <input
                  type="text"
                  id="description"
                  v-model="formData.description"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                  required
              >
            </div>

            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1" for="capacity">容量</label>
              <input
                  type="number"
                  id="capacity"
                  v-model.number="formData.capacity"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                  min="1"
                  required
              >
            </div>

            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">状态</label>
              <div class="flex items-center space-x-4">
                <label class="inline-flex items-center">
                  <input
                      type="radio"
                      name="status"
                      value="true"
                      v-model="formData.open"
                      class="form-radio text-blue-500 h-4 w-4"
                  >
                  <span class="ml-2">开放</span>
                </label>
                <label class="inline-flex items-center">
                  <input
                      type="radio"
                      name="status"
                      value="false"
                      v-model="formData.open"
                      class="form-radio text-blue-500 h-4 w-4"
                  >
                  <span class="ml-2">关闭</span>
                </label>
              </div>
            </div>

            <div class="flex justify-end gap-3 mt-6">
              <button
                  type="button"
                  @click="closeModal"
                  class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
              >
                取消
              </button>
              <button
                  type="submit"
                  class="px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition-colors"
              >
                {{ isEditing ? '保存修改' : '添加' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 确认删除模态框 -->
    <div v-if="deleteModalVisible" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg shadow-xl w-full max-w-md mx-4 transform transition-all duration-300 scale-100">
        <div class="p-6">
          <h3 class="text-lg font-bold text-gray-800 mb-2">确认删除</h3>
          <p class="text-gray-600 mb-6">你确定要删除这个餐台吗？此操作不可撤销。</p>
          <div class="flex justify-end gap-3">
            <button
                @click="deleteModalVisible = false"
                class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
            >
              取消
            </button>
            <button
                @click="confirmDelete"
                class="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded-lg transition-colors"
            >
              确认删除
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, reactive, onMounted} from 'vue'
import axios from "axios";

// 餐台数据
const desks = ref([])


const fetchDeskData = async () => {
  const response = await axios.get("http://localhost:8080/api/desk", {
    headers: {
      "Content-Type": "application/json",
    },
  });
  desks.value = response.data
}

// 搜索相关
const searchTerm = ref('')
const filteredDesks = computed(() =>
    desks.value.filter(desk =>
        desk.code.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
        desk.description.toLowerCase().includes(searchTerm.value.toLowerCase())
    )
)

// 模态框状态
const modalVisible = ref(false)
const deleteModalVisible = ref(false)
const isEditing = ref(false)
const currentDeskId = ref(null)

// 表单数据
const formData = reactive({
  id: null,
  code: '',
  description: '',
  capacity: 1,
  open: true
})

// 打开添加模态框
const openAddModal = () => {
  isEditing.value = false
  formData.id = null
  formData.code = ''
  formData.description = ''
  formData.capacity = 1
  formData.open = true
  modalVisible.value = true
}

// 打开编辑模态框
const openEditModal = (desk) => {
  isEditing.value = true
  formData.id = desk.id
  formData.code = desk.code
  formData.description = desk.description
  formData.capacity = desk.capacity
  formData.open = deske.open
  modalVisible.value = true
}

// 关闭模态框
const closeModal = () => {
  modalVisible.value = false
}

// 提交表单
const submitForm = async () => {

  let response

  if (isEditing.value) {
    // 更新餐台
    response = await axios.put(`http://localhost:8080/api/desk/${formData.id}`, {
      id: formData.id,
      code: formData.code,
      description: formData.description,
      capacity: formData.capacity,
      open: formData.open
    })

    const index = desks.value.findIndex(t => t.id === formData.id)
    if (index !== -1) {
      desks.value[index] = response.data
    }
  } else {
    // 新增餐台
    response = await axios.post('http://localhost:8080/api/desk', {
      code: formData.code,
      description: formData.description,
      capacity: formData.capacity,
      open: formData.open
    })

    desks.value.push(response.data)
  }

  modalVisible.value = false
}

// 删除餐台
const deleteDesk = (id) => {
  currentDeskId.value = id
  deleteModalVisible.value = true
}

// 确认删除
const confirmDelete = () => {
  desks.value = desks.value.filter(t => t.id !== currentDeskId.value)
  deleteModalVisible.value = false
  currentDeskId.value = null
}

onMounted(fetchDeskData)
</script>
