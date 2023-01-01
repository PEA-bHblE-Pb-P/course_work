<template>
  <div class="location m-2">
    <h3>{{ location.name }}</h3>
    <p>{{ location.lat }} {{ location.lot }}</p>
    <p>{{ location.type }}</p>
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
