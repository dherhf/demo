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
      @edit-closed="handleEditClosed"
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
      <div class="flex gap-2">
        <!-- 取消按钮 -->
        <button
          v-if="isAdding"
          @click="cancelAdd"
          class="px-4 py-2 rounded-lg bg-gray-500 hover:bg-gray-600 text-white transition-colors flex items-center"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-4 h-4 mr-1"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
          取消
        </button>

        <!-- 保存编辑按钮 -->
        <button
          v-if="isEditing"
          @click="saveEdit"
          class="px-4 py-2 rounded-lg bg-green-500 hover:bg-green-600 text-white transition-colors flex items-center"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-4 h-4 mr-1"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m4.5 12.75 6 6 9-13.5"
            />
          </svg>
          保存
        </button>

        <!-- 添加/保存按钮 -->
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
            class="w-4 h-4 mr-1"
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
import { ref, onMounted, reactive, computed } from "vue";
import axios from "axios";

const tableRef = ref(null);
const customers = ref([]);
const loading = ref(false);
const error = ref(null);
const sortConfig = ref({});
const isAdding = ref(false);
const isEditing = ref(false);
const newCustomer = ref(null);

const editConfig = reactive({
  trigger: "click",
  mode: "cell",
});

const formatPhone = ({ cellValue }) => {
  // 如果单元格值为空，直接返回
  if (!cellValue) return cellValue;
  // 检查是否为有效的11位手机号
  return /^\d{11}$/.test(cellValue)
    ? cellValue.replace(/(\d{3})(\d{4})(\d{4})/, "$1 $2 $3")
    : cellValue;
};

// 处理添加或保存操作
const handleAddOrSave = async () => {
  if (isAdding.value) {
    // 当前是添加状态，执行保存新增记录
    await saveNewCustomer();
  } else {
    // 当前不是添加状态，执行添加新行
    addNewRow();
  }
};

// 添加新行
const addNewRow = () => {
  const newRow = {
    name: "",
    address: "",
    phone: "",
    cardID: "",
    orderIds: [],
    _isNew: true, // 标记这是新添加的行
  };

  customers.value.push(newRow); // 在表格底部添加新行
  newCustomer.value = newRow;
  isAdding.value = true;

  // 等待DOM更新后，自动激活第一行的第一个可编辑单元格
  setTimeout(() => {
    if (tableRef.value) {
      tableRef.value.setActiveCell(newRow, "name");
    }
  }, 100);
};

// 保存新客户
const saveNewCustomer = async () => {
  if (!newCustomer.value) return;

  try {
    loading.value = true;
    // 准备发送到服务器的数据（移除临时属性）
    const customerData = {
      name: newCustomer.value.name.trim(),
      address: newCustomer.value.address?.trim() || "",
      phone: newCustomer.value.phone?.trim() || "",
      cardID: newCustomer.value.cardID?.trim() || "",
      orderIds: [],
    };

    // 发送到服务器
    const response = await axios.post(
      "http://localhost:8080/api/customers",
      customerData,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    // 更新本地数据：用服务器返回的数据替换临时数据
    const index = customers.value.findIndex(
      (c) => c.id === newCustomer.value.id
    );
    if (index !== -1) {
      customers.value[index] = {
        ...response.data,
        orderIds: response.data.orderIds || 0,
      };
    }

    // 重置状态
    isAdding.value = false;
    newCustomer.value = null;
  } catch (err) {
    alert(err.response?.data?.message || "保存失败，请重试");
  } finally {
    loading.value = false;
  }
};

// 取消添加
const cancelAdd = () => {
  if (newCustomer.value) {
    // 从列表中移除新添加的行
    const index = customers.value.findIndex(
      (c) => c.id === newCustomer.value.id
    );
    if (index !== -1) {
      customers.value.splice(index, 1);
    }
  }

  isAdding.value = false;
  newCustomer.value = null;
};

// 处理编辑完成事件
const handleEditClosed = ({ row, column }) => {
  if (row._isNew && newCustomer.value && row.id === newCustomer.value.id) {
    // 更新新客户的数据
    newCustomer.value = { ...row };
  }
};

// 保存编辑
const saveEdit = async () => {
  try {
    loading.value = true;

    // 获取所有被编辑的记录
    const editedRecords = tableRef.value.getEditableRecords();

    if (editedRecords.length === 0) {
      console.log("没有需要保存的更改");
      return;
    }

    // 这里可以批量保存或逐个保存
    for (const record of editedRecords) {
      if (!record._isNew) {
        // 只处理现有记录的编辑
        await axios.put(
          `http://localhost:8080/api/customers/${record.id}`,
          {
            name: record.name?.trim(),
            address: record.address?.trim() || "",
            phone: record.phone?.trim() || "",
            cardID: record.cardID?.trim() || "",
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
      }
    }

    console.log("编辑保存成功");

    // 重新获取数据以确保同步
    await fetchData();
  } catch (err) {
    console.error("保存编辑失败:", err);
    alert(err.response?.data?.message || "保存失败，请重试");
  } finally {
    loading.value = false;
  }
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

    // 重置添加状态
    isAdding.value = false;
    newCustomer.value = null;
  } catch (err) {
    console.error("获取顾客数据失败:", err);
    error.value =
      err.response?.data?.message ||
      "无法连接到服务器，请检查API地址或服务器状态";
  } finally {
    loading.value = false;
  }
};

onMounted(fetchData);
</script>
