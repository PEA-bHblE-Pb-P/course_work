<template>
  <h1 class="font-rock-salt text-2xl">Character id: {{ characterId }}</h1>
  <Location :location="location"/>
  <PeopleNearBy/>
</template>

<script>
import Location from "./Location.vue";
import PeopleNearBy from "./PeopleNearBy.vue";

export default {
  name: "Profile",
  components: {PeopleNearBy, Location},
  computed: {
    characterId() {
      return this.$store.state.id;
    },
    location() {
      return this.$store.state.location
    }
  },
}
</script>