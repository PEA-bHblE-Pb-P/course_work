<template>
  <div class="location">
    <h3>{{ location.name }}</h3>
    <p>{{ location.lat }} {{ location.lot }}</p>
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
