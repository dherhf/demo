<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-y-auto">
      <Dashboard />
    </main>
  </div>
</template>

<script setup>
import Navbar from "@/components/Navbar.vue";
import Dashboard from "@/components/Dashboard.vue";
</script>
