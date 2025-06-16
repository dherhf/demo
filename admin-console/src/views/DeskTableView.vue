<template>
  <div class="flex flex-col h-screen">
    <Navbar />
    <main class="flex-1 pt-16 overflow-hidden">
      <DeskTable/>
    </main>
  </div>
</template>
<script setup>
import DeskTable from "@/components/DeskTable.vue";
import Navbar from "@/components/Navbar.vue";
</script>