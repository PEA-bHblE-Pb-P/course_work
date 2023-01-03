<template>
  <h1 class="font-rock-salt text-2xl">Character id: {{ characterId }}</h1>
  <h1> {{ character.name }}</h1>
</template>

<script>
import Location from "./Location.vue";
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
  created() {
    this.character = character(this.characterId);
    console.log(this.character);
  }
}
</script>