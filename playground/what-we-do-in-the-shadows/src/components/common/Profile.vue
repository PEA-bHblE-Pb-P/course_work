<template>
  <Character :character="character"/>
</template>

<script>
import PeopleNearBy from "./PeopleNearBy.vue";
import {character} from "../../api.js";
import Character from "./Character.vue";

export default {
  name: "Profile",
  components: {Character, PeopleNearBy},
  computed: {
    characterId() {
      return this.$store.state.id;
    },
  },
  data() {
    return {
      character: {
        name: ""
      }
    }
  },
  async beforeCreate() {
    this.character = await character(this.characterId);
  }
}
</script>