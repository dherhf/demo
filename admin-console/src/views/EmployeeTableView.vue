<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-hidden">
      <EmployeeTable/>
    </main>
  </div>
</template>

<script setup>
import Navbar from "@/components/Navbar.vue";
import EmployeeTable from "@/components/EmployeeTable.vue";
</script>