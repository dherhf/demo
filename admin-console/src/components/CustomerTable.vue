<template>
  <!-- 加载状态 -->
  <div v-if="loading" class="py-20 flex flex-col items-center">
    <div
      class="animate-spin h-12 w-12 rounded-full border-4 border-blue-500 border-t-transparent"
    ></div>
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
  <div v-else>
    <!-- 表格展示 -->
    <div  class="bg-white rounded-xl shadow-lg overflow-hidden">
      <vxe-table
          ref="tableRef"
          :data="customers"
          border
          stripe
          show-overflow
          :loading="loading"
          :edit-config="editConfig"
          @edit-closed="handleEditClosed"
          @edit-actived="handleEditActive"
      >
        <vxe-column field="id" title="ID" width="5%"></vxe-column>

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
            :formatter="formatCardId"
            :edit-render="{ name: 'input' }"
        ></vxe-column>

        <vxe-column
            field="orderIds"
            title="订单数"
            width="5%"
            align="center"
            :formatter="formatOrderCount"
        ></vxe-column>

        <!-- 操作列 -->
        <vxe-column title="操作" width="10%" align="center">
          <template #default="{ row }">
            <button
                @click="deleteCustomer(row)"
                class="px-2 py-1 rounded-md transition-colors flex items-center justify-center mx-auto"
                :class="[
              deletingIds.has(row.id)
                ? 'bg-gray-400 cursor-not-allowed'
                : 'bg-red-500 hover:bg-red-600',
              'text-white'
            ]"
                :disabled="loading || deletingIds.has(row.id)"
                :title="row._isNew ? '删除新行' : '删除客户'"
            >
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4" :class="{ 'animate-spin': deletingIds.has(row.id) }">
                <path v-if="!deletingIds.has(row.id)" stroke-linecap="round" stroke-linejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"/>
                <path v-else stroke-linecap="round" stroke-linejoin="round" d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99"/>
              </svg>
            </button>
          </template>
        </vxe-column>
      </vxe-table>
    </div>

    <!-- 底部操作区域 -->
    <div v-if="!loading" class="mt-4 flex justify-between items-center text-sm text-gray-500">
        <span>共 {{ customers.length }} 条记录</span>
        <div class="flex gap-2">
          <!-- 取消编辑按钮 -->
          <button v-if="isEditing && !isAdding" @click="cancelEdit" class="px-4 py-2 rounded-lg bg-gray-500 hover:bg-gray-600 text-white transition-colors flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>

          <!-- 保存编辑按钮 -->
          <button v-if="isEditing && !isAdding" @click="saveEdit" class="px-4 py-2 rounded-lg bg-green-500 hover:bg-green-600 text-white transition-colors flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="m4.5 12.75 6 6 9-13.5"/>
            </svg>
          </button>

          <!-- 取消新增按钮 -->
          <button v-if="isAdding" @click="cancelAdd" class="px-4 py-2 rounded-lg bg-gray-500 hover:bg-gray-600 text-white transition-colors flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>

          <!-- 保存新增按钮 -->
          <button v-if="isAdding" @click="saveNewCustomer" class="px-4 py-2 rounded-lg bg-green-500 hover:bg-green-600 text-white transition-colors flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="m4.5 12.75 6 6 9-13.5"/>
            </svg>
          </button>

          <!-- 添加按钮 -->
          <button v-if="!isEditing && !isAdding" @click="addNewRow" class="px-4 py-2 rounded-lg bg-blue-500 hover:bg-blue-600 text-white focus:outline-none transition-colors duration-200 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
            </svg>
          </button>
        </div>
    </div>

  </div>

</template>

<script setup>
import { ref, onMounted, reactive } from "vue";
import axios from "axios";

const tableRef = ref(null);
const customers = ref([]);
const loading = ref(false);
const error = ref(null);
const isAdding = ref(false);
const isEditing = ref(false);
const newCustomer = ref(null);
const editedRows = ref(new Set()); // 跟踪被编辑的行
const deletingIds = ref(new Set()); // 跟踪正在删除的记录ID

const editConfig = reactive({
  trigger: "dblclick",
  mode: "cell",
  enabled: true,
});

const formatPhone = ({ cellValue }) => {
  if (!cellValue) return cellValue;
  // 检查是否为有效的11位手机号
  return /^\d{11}$/.test(cellValue)
    ? cellValue.replace(/(\d{3})(\d{4})(\d{4})/, "$1 $2 $3")
    : cellValue;
};

const formatOrderCount = ({ cellValue }) => {
  return cellValue ? cellValue.length : 0;
}

const formatCardId = ({ cellValue }) => {
  return cellValue ? cellValue : '非会员';
}

// 添加新行
const addNewRow = () => {
  const newRow = {
    id: null,
    name: '',
    address: '',
    phone: '',
    cardID: '',
    orderIds: [],
    _isNew: true // 标记这是新添加的行
  };
  
  customers.value.push(newRow); // 在表格顶部添加新行
  newCustomer.value = newRow;
  isAdding.value = true;
  
  // 等待DOM更新后，自动激活第一行的第一个可编辑单元格
    tableRef.value.setActiveCell(newRow, 'name');

};

// 保存新客户
const saveNewCustomer = async () => {
  if (!newCustomer.value) return;
  
  try {
    loading.value = true;
    
    // 准备发送到服务器的数据（移除临时属性）
    const customerData = {
      name: newCustomer.value.name.trim(),
      address: newCustomer.value.address?.trim() || '',
      phone: newCustomer.value.phone?.trim() || '',
      cardID: newCustomer.value.cardID?.trim() || '',
      orderIds: []
    };
    
    // 发送到服务器
    await axios.post('http://localhost:8080/api/customers', customerData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    // 更新数据
    await fetchData();
    
    // 重置状态
    isAdding.value = false;
    newCustomer.value = null;
    
    console.log('客户添加成功');
    
  } catch (err) {
    console.error('保存客户失败:', err);
    alert(err.response?.data?.message || '保存失败，请重试');
  } finally {
    loading.value = false;
  }
};

// 取消添加
const cancelAdd = () => {
  if (newCustomer.value) {
    // 从列表中移除新添加的行
    const index = customers.value.findIndex(c => c._isNew === true);
    if (index !== -1) {
      customers.value.splice(index, 1);
    }
  }
  
  isAdding.value = false;
  newCustomer.value = null;
  console.log('已取消新增操作');
};

// 处理编辑激活事件
const handleEditActive = ({ row }) => {
  if (!row._isNew) {
    // 如果不是新行，标记为编辑状态
    editedRows.value.add(row.id);
    isEditing.value = true;
  }
};

// 处理编辑完成事件
const handleEditClosed = ({ row}) => {
  if (row._isNew && newCustomer.value && row.id === newCustomer.value.id) {
    // 更新新客户的数据
    newCustomer.value = { ...row };
  } else if (!row._isNew) {
    // 对于现有行，检查是否还有其他编辑中的行
    setTimeout(() => {
      const activeRecords = tableRef.value?.getEditableRecords?.() || [];
      const hasEditingRows = activeRecords.some(record => !record._isNew);
      isEditing.value = hasEditingRows || editedRows.value.size > 0;
    }, 100);
  }
};

// 保存编辑（用于编辑现有记录）
const saveEdit = async () => {
  try {
    loading.value = true;
    
    // 获取所有被编辑的记录
    const editableRecords = tableRef.value?.getEditableRecords?.() || [];
    const recordsToSave = editableRecords.filter(record => !record._isNew);
    
    // 如果有正在编辑的记录，使用它们；否则使用标记的已编辑记录
    const toSave = recordsToSave.length > 0 
      ? recordsToSave 
      : customers.value.filter(customer => editedRows.value.has(customer.id));
    
    // 批量保存编辑的记录
    const savePromises = toSave.map(async (record) => {
      try {
        const response = await axios.put(`http://localhost:8080/api/customers/${record.id}`, {
          id: record.id,
          name: record.name?.trim(),
          address: record.address?.trim() || '',
          phone: record.phone?.trim() || '',
          cardID: record.cardID?.trim() || '',
        }, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        return { success: true, id: record.id, data: response.data };
      } catch (error) {
        console.error(`保存客户 ${record.id} 失败:`, error);
        return { success: false, id: record.id, error: error.response?.data?.message || '保存失败' };
      }
    });
    
    const results = await Promise.all(savePromises);
    
    // 检查保存结果
    const failures = results.filter(r => !r.success);
    if (failures.length > 0) {
      alert(`${failures.length} 条记录保存失败，请检查数据后重试`);
    } else {
      console.log('所有编辑保存成功');
      
      // 更新本地数据
      results.forEach(result => {
        if (result.success) {
          const index = customers.value.findIndex(c => c.id === result.id);
          if (index !== -1) {
            customers.value[index] = { ...customers.value[index], ...result.data };
          }
        }
      });
    }
    
    // 重置编辑状态
    isEditing.value = false;
    editedRows.value.clear();
    
    // 清除表格的编辑状态
    tableRef.value?.clearEdit?.();
    
  } catch (err) {
    console.error('批量保存失败:', err);
    alert('保存失败，请重试');
  } finally {
    loading.value = false;
  }
};

// 取消编辑
const cancelEdit = () => {
  // 清除表格的编辑状态
  tableRef.value?.clearEdit?.();

  // 重置编辑状态
  isEditing.value = false;
  editedRows.value.clear();

  // 重新获取数据以恢复原始状态
  fetchData();
};

// 删除客户
const deleteCustomer = async (row) => {
  // 如果是新行，直接从本地删除
  if (row._isNew) {
    const index = customers.value.findIndex(c => c.id === row.id);
    if (index !== -1) {
      customers.value.splice(index, 1);
      
      // 如果删除的是当前新增的客户，重置新增状态
      if (newCustomer.value && newCustomer.value.id === row.id) {
        isAdding.value = false;
        newCustomer.value = null;
      }
    }
    console.log('已删除新添加的行');
    return;
  }
  
  // 确认删除
  const customerName = row.name || `ID: ${row.id}`;
  if (!confirm(`确定要删除客户 "${customerName}" 吗？此操作不可撤销。`)) {
    return;
  }
  
  try {
    // 标记为正在删除
    deletingIds.value.add(row.id);
    
    // 发送删除请求到服务器
    await axios.delete(`http://localhost:8080/api/customers/${row.id}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    
    // 从本地列表中移除
    const index = customers.value.findIndex(c => c.id === row.id);
    if (index !== -1) {
      customers.value.splice(index, 1);
    }
    
    // 从编辑跟踪中移除
    editedRows.value.delete(row.id);
    
    console.log(`客户 "${customerName}" 删除成功`);
    
  } catch (err) {
    console.error('删除客户失败:', err);
    alert(err.response?.data?.message || '删除失败，请重试');
  } finally {
    // 移除删除标记
    deletingIds.value.delete(row.id);
  }
};

const fetchData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.get("http://localhost:8080/api/customers", {
      headers: {
        "Content-Type": "application/json",
      },
    });
    customers.value = response.data || [];
    tableRef.value?.clearCheckbox?.();
    
    // 重置添加状态
    isAdding.value = false;
    newCustomer.value = null;
    isEditing.value = false;
    editedRows.value.clear();
    deletingIds.value.clear();
    
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