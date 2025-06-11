<template>
  <div>
    <div>
      <h1>客户列表</h1>
      <button @click="handleAddCustomer">
        <i class="fa fa-plus"></i> 添加客户
      </button>
    </div>

    <!-- 搜索和筛选 -->
    <div>
      <div>
        <div>
          <label>搜索</label>
          <div>
            <input
                type="text"
                placeholder="搜索客户名称或电话..."
                v-model="searchQuery"
                @keyup.enter="filterCustomers"
            >
            <div @click="filterCustomers">
              <i class="fa fa-search"></i>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 客户列表 -->
    <div>
      <div>
        <table>
          <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">名称</th>
            <th scope="col">地址</th>
            <th scope="col">电话</th>
            <th scope="col">身份证号</th>
            <th scope="col">订单数</th>
            <th scope="col">操作</th>
          </tr>
          </thead>
          <tbody>
          <!-- 加载状态 -->
          <tr v-if="loading">
            <td colspan="7">
              <div>
                <i class="fa fa-spinner fa-spin"></i>
                <span>加载中...</span>
              </div>
            </td>
          </tr>

          <!-- 错误状态 -->
          <tr v-else-if="error">
            <td colspan="7">
              <i class="fa fa-exclamation-circle"></i>
              {{ error }}
            </td>
          </tr>

          <!-- 空状态 -->
          <tr v-else-if="filteredData.length === 0">
            <td colspan="7">
              <i></i>
              未找到客户记录
            </td>
          </tr>

          <!-- 客户数据 -->
          <tr v-else v-for="customer in filteredData" :key="customer.id">
            <td>{{ customer.id }}</td>
            <td>{{ customer.name }}</td>
            <td>{{ customer.address }}</td>
            <td>{{ customer.phone }}</td>
            <td>
              {{ customer.cardID ? customer.cardID.replace(/(\d{6})\d+(\d{4})/, '$1****$2') : '-' }}
            </td>
            <td>
                <span>
                  {{ customer.orders.length }}
                </span>
            </td>
            <td>
              <button @click="handleEditCustomer(customer.id)">
                <i>修改</i>
              </button>
              <button @click="handleDeleteCustomer(customer.id)">
                <i>删除</i>
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue';
import axios from 'axios';

// 页面加载时获取数据
onMounted(() => {
  fetchData();
});

const data = ref([]);
const loading = ref(false);
const error = ref(null);
const searchQuery = ref('');

// 计算属性，根据搜索条件过滤客户数据
const filteredData = computed(() => {
  if (!searchQuery.value.trim()) {
    return data.value;
  }
  
  const query = searchQuery.value.toLowerCase().trim();
  return data.value.filter(customer => 
    (customer.name && customer.name.toLowerCase().includes(query)) ||
    (customer.phone && customer.phone.toLowerCase().includes(query))
  );
});

const fetchData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.get('http://localhost:8080/api/customers', {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    // 直接使用响应数据
    data.value = response.data;
  } catch (err) {
    error.value = err.response?.data?.message || err.message;
  } finally {
    loading.value = false;
  }
}

// 搜索客户
const filterCustomers = () => {
  // 已经通过计算属性实时过滤，这里可以添加其他搜索逻辑
};

// 处理添加客户
const handleAddCustomer = () => {
  // 这里添加打开添加客户表单的逻辑
  console.log('添加客户');
};

// 处理编辑客户
const handleEditCustomer = (id) => {
  // 这里添加打开编辑客户表单的逻辑
  console.log('编辑客户', id);
};

// 处理删除客户
const handleDeleteCustomer = async (id) => {
  if (!confirm('确定要删除这个客户吗？')) {
    return;
  }

  try {
    loading.value = true;
    error.value = null;
    
    const response = await axios.delete(`api/customer/${id}`);
    
    if (response.data.success) {
      // 从列表中移除已删除的客户
      data.value = data.value.filter(customer => customer.id !== id);
      // 显示成功消息
      alert('客户删除成功');
    } else {
      error.value = response.data.message;
    }
  } catch (err) {
    error.value = err.response?.data?.message || err.message;
  } finally {
    loading.value = false;
  }
};
</script>
    