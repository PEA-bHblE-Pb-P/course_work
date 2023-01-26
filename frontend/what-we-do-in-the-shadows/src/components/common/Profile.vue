<template>
  <div class="w-fit">
    <Character :character="character" />
    <Location v-if="location" :titled="true" :location="location" />
    <div v-if="!location" class="p-5 m-5">
      <img
        class="w-40"
        src="/rip.png"
        alt="photo"
      />
      <h1 class="mdi-font-awesome mb-2">О нём слогали легенды: {{ character.history }}</h1>
    </div>
  </div>
</template>

<script>
import { character, get_location } from "../../api.js";
import Character from "./Character.vue";
import Location from "./Location.vue";
import { typedLocation } from "../../mapper.js";

export default {
  name: "Profile",
  components: { Location, Character },
  computed: {
    characterId() {
      return this.$store.state.id;
    },
    characterByState() {
      return this.$store.state.character;
    },
  },
  data() {
    return {
      character: {},
      location: undefined,
    };
  },
  async beforeMount() {
    if (this.$store.getters.shouldSetCharacter) {
      this.character = await character(this.characterId);
      this.$store.commit("setCharacter", this.character);
    } else {
      this.character = await this.characterByState;
    }

    let loc = await get_location(this.character.location);
    if (loc)
      this.location = typedLocation(loc);
  },
};
</script>
