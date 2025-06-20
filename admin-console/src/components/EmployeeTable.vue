<template>
  <div class="container mx-auto p-4">
    <!-- 搜索和添加区域 -->
    <div class="flex flex-col md:flex-row gap-4 mb-6">
      <div class="relative flex-grow">
        <input
          type="text"
          v-model="searchTerm"
          placeholder="搜索员工..."
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
        />
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
        <i class="fa fa-plus mr-2"></i>添加员工
      </button>
    </div>

    <!-- 员工列表 -->
    <div class="overflow-x-auto">
      <table class="min-w-full bg-white rounded-lg overflow-hidden">
        <thead>
          <tr class="bg-gray-100 text-left">
            <th class="px-6 py-3 text-sm font-semibold text-gray-600">ID</th>
            <th class="px-6 py-3 text-sm font-semibold text-gray-600">姓名</th>
            <th class="px-6 py-3 text-sm font-semibold text-gray-600">年龄</th>
            <th class="px-6 py-3 text-sm font-semibold text-gray-600">电话</th>
            <th class="px-6 py-3 text-sm font-semibold text-gray-600">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="employee in filteredEmployees"
            :key="employee.id"
            class="border-b border-gray-200 hover:bg-gray-50 transition-colors"
          >
            <td class="px-6 py-4 text-sm text-gray-800">{{ employee.id }}</td>
            <td class="px-6 py-4 text-sm text-gray-800">{{ employee.name }}</td>
            <td class="px-6 py-4 text-sm text-gray-800">{{ employee.age }}</td>
            <td class="px-6 py-4 text-sm text-gray-800">
              {{ employee.phone }}
            </td>
            <td class="px-6 py-4 text-sm">
              <button
                @click="openEditModal(employee)"
                class="text-blue-500 hover:text-blue-700 mr-3 transition-colors"
              >
                <i class="fa fa-edit"></i> 编辑
              </button>
              <button
                @click="deleteEmployee(employee.id)"
                class="text-red-500 hover:text-red-700 transition-colors"
              >
                <i class="fa fa-trash"></i> 删除
              </button>
            </td>
          </tr>
          <tr v-if="!employees.length">
            <td colspan="5" class="px-6 py-4 text-center text-gray-500">
              暂无员工数据
            </td>
          </tr>
          <tr v-else-if="!filteredEmployees.length">
            <td colspan="5" class="px-6 py-4 text-center text-gray-500">
              未找到匹配的员工
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑员工模态框 -->
    <div
      v-if="modalVisible"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div
        class="bg-white rounded-lg shadow-xl w-full max-w-md mx-4 transform transition-all duration-300 scale-100"
      >
        <div class="p-6">
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-bold text-gray-800">
              {{ isEditing ? "编辑员工" : "添加员工" }}
            </h3>
            <button
              @click="closeModal"
              class="text-gray-400 hover:text-gray-600"
            >
              <i class="fa fa-times"></i>
            </button>
          </div>

          <form @submit.prevent="submitForm">
            <div class="mb-4">
              <label
                class="block text-sm font-medium text-gray-700 mb-1"
                for="name"
                >姓名</label
              >
              <input
                type="text"
                id="name"
                v-model="formData.name"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                required
              />
            </div>

            <div class="mb-4">
              <label
                class="block text-sm font-medium text-gray-700 mb-1"
                for="age"
                >年龄</label
              >
              <input
                type="number"
                id="age"
                v-model.number="formData.age"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                min="1"
                max="100"
                required
              />
            </div>

            <div class="mb-4">
              <label
                class="block text-sm font-medium text-gray-700 mb-1"
                for="phone"
                >电话</label
              >
              <input
                type="tel"
                id="phone"
                v-model="formData.phone"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                pattern="^1[3-9]\d{9}$"
                required
              />
              <p class="text-xs text-gray-500 mt-1">请输入有效的11位手机号码</p>
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
                {{ isEditing ? "保存修改" : "添加" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 确认删除模态框 -->
    <div
      v-if="deleteModalVisible"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div
        class="bg-white rounded-lg shadow-xl w-full max-w-md mx-4 transform transition-all duration-300 scale-100"
      >
        <div class="p-6">
          <h3 class="text-lg font-bold text-gray-800 mb-2">确认删除</h3>
          <p class="text-gray-600 mb-6">
            你确定要删除这名员工吗？此操作不可撤销。
          </p>
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
import { computed, onMounted, reactive, ref } from "vue";
import axios from "axios";

// 员工数据
const employees = ref([]);

const loadingEmployee = ref(false);
const error = ref(null);

const fetchEmployeeData = async () => {
  loadingEmployee.value = true;
  error.value = null;
  try {
    const response = await axios.get("http://localhost:8080/api/employee", {
      headers: {
        "Content-Type": "application/json",
      },
    });
    employees.value = response.data;
  } catch (err) {
    error.value = err.message || "获取菜品数据失败";
    console.error("获取菜品数据失败:", err);
  } finally {
    loadingEmployee.value = false;
  }
};

// 搜索相关
const searchTerm = ref("");
const filteredEmployees = computed(() =>
  employees.value.filter((employee) =>
    employee.name.toLowerCase().includes(searchTerm.value.toLowerCase())
  )
);

// 模态框状态
const modalVisible = ref(false);
const deleteModalVisible = ref(false);
const isEditing = ref(false);
const currentEmployeeId = ref(null);

// 表单数据
const formData = reactive({
  id: null,
  name: "",
  age: "",
  phone: "",
});

// 打开添加模态框
const openAddModal = () => {
  isEditing.value = false;
  formData.id = null;
  formData.name = "";
  formData.age = "";
  formData.phone = "";
  modalVisible.value = true;
};

// 打开编辑模态框
const openEditModal = (employee) => {
  isEditing.value = true;
  formData.id = employee.id;
  formData.name = employee.name;
  formData.age = employee.age;
  formData.phone = employee.phone;
  modalVisible.value = true;
};

// 关闭模态框
const closeModal = () => {
  modalVisible.value = false;
};

// 提交表单
const submitForm = async () => {
  let response;
  if (isEditing.value) {
    // 更新员工信息
    response = await axios.put(
      `http://localhost:8080/api/employee/${formData.id}`,
      {
        id: formData.id,
        name: formData.name,
        age: formData.age,
        phone: formData.phone,
      }
    );

    const index = employees.value.findIndex((e) => e.id === formData.id);
    if (index !== -1) {
      employees.value[index] = response.data;
    }
  } else {
    response = await axios.post("http://localhost:8080/api/employee", {
      name: formData.name,
      age: formData.age,
      phone: formData.phone,
    });

    employees.value.push(response.data);
  }

  modalVisible.value = false;
};

// 删除员工
const deleteEmployee = (id) => {
  currentEmployeeId.value = id;
  deleteModalVisible.value = true;
};

// 确认删除
const confirmDelete = async () => {
  await axios.delete(
    `http://localhost:8080/api/employee/${currentEmployeeId.value}`
  );
  employees.value = employees.value.filter(
    (e) => e.id !== currentEmployeeId.value
  );
  deleteModalVisible.value = false;
  currentEmployeeId.value = null;
};

onMounted(fetchEmployeeData);
</script>
