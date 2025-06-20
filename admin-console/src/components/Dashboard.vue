<template>
  <!-- 跳转按钮区域 -->
  <div class="flex gap-4 mt-6">
    <button
      @click="goToOrder"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      点菜
    </button>
    <button
      @click="goToDish"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      菜品管理
    </button>
    <button
      @click="goToCustomer"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      顾客管理
    </button>
    <button
      @click="goTOEmployee"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      员工管理
    </button>
    <button
      @click="goToDesk"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      餐台管理
    </button>
    <button
      @click="goTOHistoricalOrders"
      class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      历史订单
    </button>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goToOrder = () => {
  router.push("/order");
};

const goToDish = () => {
  router.push("/dish");
};

const goToCustomer = () => {
  router.push("/customers");
};

const goTOEmployee = () => {
  router.push("/employee");
};

const goToDesk = () => {
  router.push("/desk");
};

const goTOHistoricalOrders = () => {
  router.push("/historical-orders");
};
</script>
