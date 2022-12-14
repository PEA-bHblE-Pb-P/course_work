<template>
  <h1 v-for="person in people">{{ person.id }}</h1>
</template>

<script>
import {people_nearby} from "../../api.js";

export default {
  name: "PeopleNearBy",
  data() {
    return {
      people: []
    }
  },
  setup() {
    this.people = people_nearby()
  }
}
</script>
