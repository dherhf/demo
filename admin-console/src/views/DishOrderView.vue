<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <div class="flex-1 pt-16 overflow-y-auto">
      <DishOrder />
    </div>
  </div>
</template>

<script setup>
import Navbar from "@/components/Navbar.vue";
import DishOrder from "@/components/DishOrder.vue";
</script>
