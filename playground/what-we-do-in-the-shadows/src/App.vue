<template>
  <div class="w-full">
    <Header />
    <div class="sidebar"></div>
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import Header from "./components/layout/Header.vue";
</script>
