<template>
  <!-- 页面标题 -->
  <h1 class="text-3xl font-bold mb-6 text-center text-blue-700">
    顾客信息总览
  </h1>

  <!-- 加载状态 -->
  <div v-if="loading" class="py-20 flex flex-col items-center">
    <div
      class="animate-spin h-12 w-12 rounded-full border-4 border-blue-500 border-t-transparent"
    ></div>
    <p class="mt-4 text-lg text-gray-600">正在加载顾客数据...</p>
  </div>

  <!-- 错误状态 -->
  <div v-else-if="error" class="py-20 text-center text-red-600">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      class="mx-auto h-12 w-12 text-red-500"
      fill="none"
      viewBox="0 0 24 24"
      stroke="currentColor"
    >
      <path
        stroke-linecap="round"
        stroke-linejoin="round"
        stroke-width="2"
        d="M12 9v2m0 4h.01M4.93 4.93l14.14 14.14M4.93 19.07L19.07 4.93"
      />
    </svg>
    <p class="text-xl mt-4">{{ error }}</p>
    <button
      @click="fetchData"
      class="mt-4 px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition"
    >
      重新加载
    </button>
  </div>

  <!-- 表格展示 -->
  <div v-else class="bg-white rounded-xl shadow-lg overflow-hidden">
    <vxe-table
      ref="tableRef"
      :data="customers"
      border
      stripe
      show-overflow
      :loading="loading"
      :edit-config="editConfig"
    >
      <vxe-column field="id" title="ID" width="10%"></vxe-column>

      <vxe-column
        field="name"
        title="姓名"
        width="20%"
        :edit-render="{ name: 'input' }"
      ></vxe-column>

      <vxe-column
        field="address"
        title="地址"
        min-width="20%"
        :edit-render="{ name: 'input' }"
      ></vxe-column>

      <vxe-column
        field="phone"
        title="电话"
        width="20%"
        :formatter="formatPhone"
        :edit-render="{ name: 'input' }"
      ></vxe-column>

      <vxe-column
        field="cardID"
        title="会员卡号"
        width="20%"
        :edit-render="{ name: 'input' }"
      ></vxe-column>

      <vxe-column
        field="orderIds"
        title="订单数"
        width="10%"
        align="center"
      ></vxe-column>
    </vxe-table>
  </div>
  <!-- 底部操作区域 -->
  <div>
    <div
      v-if="!loading"
      class="mt-4 flex justify-between items-center text-sm text-gray-500"
    >
      <span>共 {{ customers.length }} 条记录</span>
      <div class="flex items-center space-x-2">
        <button
          v-if="isEditing"
          @click="saveEdit"
          class="px-4 py-2 rounded-lg bg-green-500 hover:bg-green-600 text-white transition-colors"
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
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m4.5 12.75 6 6 9-13.5"
            />
          </svg>
        </button>
        <button
          v-if="!isEditing"
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
import { ref, onMounted, reactive, nextTick } from "vue";
import axios from "axios";

const tableRef = ref(null);
const customers = ref([]);
const loading = ref(false);
const error = ref(null);
const sortConfig = ref({});
const isAdding = ref(false); // 添加状态标记
const isEditing = ref(false); // 编辑状态标记

const editConfig = reactive({
  trigger: "click",
  mode: "cell",
});

const formatPhone = ({ cellValue }) => {
  if (!cellValue) return "未填写";
  return /^\d{11}$/.test(cellValue)
    ? cellValue.replace(/(\d{3})(\d{4})(\d{4})/, "$1 $2 $3")
    : cellValue;
};

const fetchData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.get("http://localhost:8080/api/customers", {
      params: sortConfig.value,
      headers: {
        "Content-Type": "application/json",
      },
    });
    customers.value = response.data || [];
    tableRef.value?.clearCheckbox?.();
  } catch (err) {
    console.error("获取顾客数据失败:", err);
    error.value =
      err.response?.data?.message ||
      "无法连接到服务器,请检查API地址或服务器状态";
  } finally {
    loading.value = false;
  }
};

// 添加操作
const handleAddOrSave = () => {
  if (isAdding.value) {
    // 取消添加模式
    isAdding.value = false;
    return;
  }

  // 进入添加模式
  isAdding.value = true;
  isEditing.value = true;

  // 生成新顾客数据
  const newCustomer = {
    id: null,
    name: "",
    address: "",
    phone: "",
    cardID: "",
    orderIds: 0,
  };

  // 添加到数据列表
  customers.value.unshift(newCustomer);

  // 延迟更新视图，确保表格正确渲染
  nextTick(() => {
    tableRef.value?.refresh();
    // 定位到新添加的行
    tableRef.value?.scrollToRow(0);
    // 激活第一个可编辑单元格
    tableRef.value?.editCell(0, "name");
  });
};

// 保存编辑
const saveEdit = () => {
  // 这里可以添加保存逻辑
  console.log("保存编辑的数据:", customers.value);
  isEditing.value = false;
  isAdding.value = false;
  // 可以在此处调用API保存数据
};

onMounted(fetchData);
</script>
