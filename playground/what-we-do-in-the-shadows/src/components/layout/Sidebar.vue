<template>
  <div class="w-full py-2 px-6 bg-red-400">
    <router-link v-for="link in links" :key="link.name" :to="link.href">
      {{ link.name }}
    </router-link>
  </div>
</template>

<script setup>
import { ref } from "vue";

const links = ref([{ name: "Typography", href: "/typography" }]);
</script>
