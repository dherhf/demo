<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-y-auto">
      <CustomerTable/>
    </main>
  </div>
</template>

<script setup>
import Navbar from "@/components/Navbar.vue";
import CustomerTable from "@/components/CustomerTable.vue";
</script>