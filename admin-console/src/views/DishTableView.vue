<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-y-auto">
      <DishTable/>
    </main>
  </div>
</template>

<script setup>
import Navbar from "@/components/Navbar.vue";
import DishTable from "@/components/DishTable.vue";
</script>