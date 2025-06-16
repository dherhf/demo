<template>

  <Navbar/>
  <OrderDetails order-id="$route.params.orderId" />
</template>

<script setup>
import OrderDetails from "@/components/OrderDetails.vue";
import Navbar from "@/components/Navbar.vue";
import { useRoute } from 'vue-router';

const route = useRoute();
</script>

