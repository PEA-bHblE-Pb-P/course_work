<template>
  <div class="location m-2 p-2">
    <h3>name: {{ location.name }}</h3>
    <p>pos: ({{ location.lat }}, {{ location.lot }})</p>
    <p>type: {{ location.type || 'none' }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  location: {
    type: Object,
    required: true,
    default: () => {},
  },
});
</script>

<style scoped>
.location {
  border: 2px solid black;
}
</style>