<template>
    <nav class="bg-white shadow-sm sticky top-0 z-10 ">
      <div class="px-4 sm:px-6 lg:px-8 py-4">
        <router-link
            to="/dashboard"
            class="flex items-center space-x-2 text-gray-700 hover:text-primary transition-colors duration-200"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
          </svg>
          <span>返回控制台</span>
        </router-link>
      </div>
    </nav>
</template>

<script setup>
</script>