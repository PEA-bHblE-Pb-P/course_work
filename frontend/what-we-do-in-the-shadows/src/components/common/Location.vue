<template>
  <div class="location m-2 p-2 mdi-font-awesome">
    <h1 v-if="titled" class="text-2xl">Location</h1>
    <h3>name: {{ location.name }}</h3>
    <p>pos: ({{ location.lat }}, {{ location.lon }})</p>
    <p>type: {{ location.type || "none" }}</p>
    <h1>vampires: {{ location.vampiresCount }}</h1>
  </div>
</template>

<script setup>
const props = defineProps({
  location: {
    type: Object,
    required: true,
    default: () => {},
  },
  titled: {
    type: Boolean,
    required: true,
    default: true
  }
});
</script>

<style scoped>
.location {
  border: 2px solid black;
}
</style>
