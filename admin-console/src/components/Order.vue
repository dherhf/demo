<template>
  <!-- 顶部选择栏 -->
  <div class="bg-white shadow-md p-4">
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <!-- 餐台选择 -->
      <div class="form-group">
        <label class="block text-gray-700 text-sm font-bold mb-1">
          选择餐台
        </label>
        <div class="relative">
          <select
            v-model="selectedDesk"
            class="block w-full pl-3 pr-10 py-2 text-base border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent rounded-md appearance-none bg-white"
            @change="handleDeskChange"
          >
            <option value="">请选择餐台</option>
            <option v-for="desk in desks" :key="desk.id" :value="desk.id">
              {{ desk.code }} ({{ desk.description }})
            </option>
          </select>
          <div
            class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
          >
            <i class="fas fa-chevron-down text-xs"></i>
          </div>
        </div>
      </div>

      <!-- 顾客选择 -->
      <div class="form-group">
        <label class="block text-gray-700 text-sm font-bold mb-1">
          选择顾客
        </label>
        <div class="relative">
          <select
            v-model="selectedCustomer"
            class="block w-full pl-3 pr-10 py-2 text-base border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent rounded-md appearance-none bg-white"
            @change="handleCustomerChange"
          >
            <option value="">请选择顾客</option>
            <option
              v-for="customer in customers"
              :key="customer.id"
              :value="customer.id"
            >
              {{ customer.name }} ({{ customer.phone }})
            </option>
          </select>
          <div
            class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
          >
            <i class="fas fa-chevron-down text-xs"></i>
          </div>
        </div>
      </div>

      <!-- 员工选择 -->
      <div class="form-group">
        <label class="block text-gray-700 text-sm font-bold mb-1">
          服务员工
        </label>
        <div class="relative">
          <select
            v-model="selectedEmployee"
            class="block w-full pl-3 pr-10 py-2 text-base border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent rounded-md appearance-none bg-white"
            @change="handleEmployeeChange"
          >
            <option value="">请选择员工</option>
            <option
              v-for="employee in employees"
              :key="employee.id"
              :value="employee.id"
            >
              {{ employee.name }} ({{ employee.phone }})
            </option>
          </select>
          <div
            class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
          >
            <i class="fas fa-chevron-down text-xs"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

// 表单数据
const selectedDesk = ref("");
const selectedCustomer = ref("");
const selectedEmployee = ref("");

// 错误信息
const deskError = ref("");
const customerError = ref("");
const employeeError = ref("");

// 下拉框数据
const desks = ref([]);
const customers = ref([]);
const employees = ref([]);

// 处理餐台选择变化
const handleDeskChange = (event) => {
  deskError.value = "";
  // 可以在此处添加验证逻辑
  if (
    event.target.value &&
    !desks.value.some((d) => d.id === event.target.value)
  ) {
    deskError.value = "无效的餐台选择";
  }
};

// 处理顾客选择变化
const handleCustomerChange = (event) => {
  customerError.value = "";
  // 可以在此处添加验证逻辑
  if (
    event.target.value &&
    !customers.value.some((c) => c.id === event.target.value)
  ) {
    customerError.value = "无效的顾客选择";
  }
};

// 处理员工选择变化
const handleEmployeeChange = (event) => {
  employeeError.value = "";
  // 可以在此处添加验证逻辑
  if (
    event.target.value &&
    !employees.value.some((e) => e.id === event.target.value)
  ) {
    employeeError.value = "无效的员工选择";
  }
};

// 加载数据（实际项目中应替换为API请求）
onMounted(() => {
  axios
    .get("http://localhost:8080/api/desk/open")
    .then((response) => {
      desks.value = response.data;
    })
    .catch((error) => {
      console.error("获取餐台数据失败:", error);
    });

  axios
    .get("http://localhost:8080/api/customers")
    .then((response) => {
      customers.value = response.data;
    })
    .catch((error) => {
      console.error("获取顾客数据失败:", error);
    });

  axios
    .get("http://localhost:8080/api/employee")
    .then((response) => {
      employees.value = response.data;
    })
    .catch((error) => {
      console.error("获取员工数据失败:", error);
    });
});

// 暴露选中的数据，供父组件使用
const getSelectedData = () => {
  return {
    desk: desks.value.find((t) => t.id === selectedDesk.value),
    customer: customers.value.find((c) => c.id === selectedCustomer.value),
    employee: employees.value.find((s) => s.id === selectedEmployee.value),
  };
};

defineExpose({
  getSelectedData,
});
</script>
