<template v-if="character.name">
  <div
    class="grid grid-cols-2 m-2 p-2 w-fit"
    :class="{ character: !highlight, highlight: highlight }"
  >
    <div>
      <h1 class="mdi-font-awesome text-2xl">Character</h1>
      <h1 class="mdi-font-awesome">id: {{ character.id }}</h1>
      <h1 class="mdi-font-awesome">name: {{ character.name }}</h1>
      <h1 class="mdi-font-awesome">type: {{ mapCharacterType(character.typeId) }}</h1>
      <h1 class="mdi-font-awesome mb-2">sex: {{ character.sex }}</h1>
      <h1 class="mdi-font-awesome mb-2">birthday: {{ character.birthday }}</h1>
    </div>
    <div>
      <img
        v-if="character.photoUrl"
        class="w-40"
        :src="character.photoUrl"
        alt="photo"
      />
      <img
        v-if="!character.photoUrl"
        class="w-40"
        :src="mapImage(character)"
        alt="default photo"
      />
    </div>
  </div>
</template>

<script setup>
import { mapCharacterType, mapImage } from "../../mapper.js";

const props = defineProps({
  character: {
    type: Object,
    required: true,
    default: {},
  },
  highlight: {
    type: Boolean,
    required: false,
    default: false,
  },
});
</script>

<style scoped>
.character {
  border: 2px solid black;
}
.highlight {
  border: 2px solid red;
}
</style>
