<template>
  <h1 class="font-rock-salt text-2xl">Character id: {{ characterId }}</h1>
  <h1> {{ character.name }}</h1>
</template>

<script>
import PeopleNearBy from "./PeopleNearBy.vue";
import {character} from "../../api.js";

export default {
  name: "Profile",
  components: {PeopleNearBy},
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