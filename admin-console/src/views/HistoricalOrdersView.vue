<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-y-auto">
      <HistoricalOrders/>
    </main>
  </div>

</template>

<script setup>
import HistoricalOrders from "@/components/HistoricalOrders.vue";
import Navbar from "@/components/Navbar.vue";
</script>