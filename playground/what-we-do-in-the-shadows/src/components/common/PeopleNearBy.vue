<template>
  <div class="grid grid-cols-5">
    <Character v-for="person in people" :key="person.id" :character="person"/>
  </div>
</template>

<script>
import {character, people_nearby} from "../../api.js";
import Character from "./Character.vue";

export default {
  name: "PeopleNearBy",
  components: {Character},
  data() {
    return {
      people: []
    }
  },
  async beforeCreate() {
    this.people = await people_nearby();
    console.log(this.people)
  }
}
</script>
