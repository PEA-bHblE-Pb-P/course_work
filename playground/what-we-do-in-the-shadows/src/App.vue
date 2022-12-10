<template>
  <div class="w-full">
    <Header/>
    <div class="sidebar">
      <Sidebar/>
    </div>
    <div class="content">
      <router-view class="h-3/4"/>
    </div>
  </div>
</template>

<script setup>
import Header from "./components/layout/Header.vue";
import Sidebar from "./components/layout/Sidebar.vue";
</script>