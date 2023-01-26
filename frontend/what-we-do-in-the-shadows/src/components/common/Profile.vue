<template>
  <div class="w-fit">
    <Character :character="character" />
    <Location :titled="true" :location="location" />
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
      location: {},
    };
  },
  async beforeMount() {
    if (this.$store.getters.shouldSetCharacter) {
      this.character = await character(this.characterId);
      this.$store.commit("setCharacter", this.character);
    } else {
      this.character = await this.characterByState;
    }

    this.location = typedLocation(await get_location(this.character.location));
  },
};
</script>
