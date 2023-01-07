<template>
  <span class="font-rock-salt text-2xl">Character id: {{ character.id }}</span>
  <span> {{ character.name }}</span>
</template>

<script setup>
const props = defineProps({
  character: {
    type: Object,
    required: true,
    default: () => {},
  },
});
</script>