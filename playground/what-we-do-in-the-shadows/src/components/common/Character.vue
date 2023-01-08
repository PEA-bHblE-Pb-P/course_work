<template v-if="character.name">
  <div class="grid grid-cols-2 m-2 p-2 character w-fit">
    <div>
      <h1 class="mdi-font-awesome text-2xl">Character</h1>
      <h1 class="mdi-font-awesome">id: {{ character.id }}</h1>
      <h1 class="mdi-font-awesome">name: {{ character.name }}</h1>
      <h1 class="mdi-font-awesome">typeId: {{ character.typeId }}</h1>
      <h1 class="mdi-font-awesome mb-2">sex: {{ character.sex }}</h1>
      <h1 class="mdi-font-awesome mb-2">birthday: {{ character.birthday }}</h1>

    </div>
    <div>
      <img class="w-40" v-if="character.photoUrl" :src="character.photoUrl" alt="photo">
      <img class="w-40" v-if="!character.photoUrl" :src="mapImage(character)" alt="default photo">
    </div>
  </div>
</template>

<script setup>
import {mapImage} from "../../mapper.js";

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
