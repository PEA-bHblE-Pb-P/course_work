<template>
  <PageHeader>People nearby</PageHeader>
  <div class="flex flex-wrap">
    <Character v-for="person in people" :key="person.id" :character="person"/>
  </div>
</template>

<script>
import {people_nearby} from "../../api.js";
import Character from "./Character.vue";
import PageHeader from "../layout/PageHeader.vue";

export default {
  name: "PeopleNearBy",
  components: {PageHeader, Character},
  data() {
    return {
      people: []
    }
  },
  async mounted() {
    this.people = await people_nearby();
    console.log(this.people)
  }
}
</script>
