<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-hidden">
      <OrderDetails order-id="$route.params.orderId" />
    </main>
  </div>
</template>

<script setup>
import OrderDetails from "@/components/OrderDetails.vue";
import Navbar from "@/components/Navbar.vue";
</script>

