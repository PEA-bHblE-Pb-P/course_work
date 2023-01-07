<template>
  <div class="m-2 p-2 character w-fit">
    <h1 class="mdi-font-awesome text-2xl">Character</h1>
    <h1 class="mdi-font-awesome">id: {{ character.id }}</h1>
    <h1 class="mdi-font-awesome">name: {{ character.name }}</h1>
    <h1 class="mdi-font-awesome">typeId: {{ character.typeId }}</h1>
    <h1 class="mdi-font-awesome">sex: {{ character.sex }}</h1>
  </div>
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

<style scoped>
.character {
  border: 2px solid black;
}
</style>
