<template>
  <div class="w-fit">
    <Character :character="character" />
    <Location :location="location" />
  </div>
</template>

<script>
import PeopleNearBy from "./PeopleNearBy.vue";
import { character, get_location } from "../../api.js";
import Character from "./Character.vue";
import Location from "./Location.vue";

export default {
  name: "Profile",
  components: { Location, Character, PeopleNearBy },
  computed: {
    characterId() {
      return this.$store.state.id;
    },
  },
  data() {
    return {
      character: {
        name: "",
        id: "",
        location: 1,
      },
      location: {},
    };
  },
  async beforeCreate() {
    this.character = await character(this.characterId);
    this.location = await get_location(this.character.location);
  },
};
</script>
