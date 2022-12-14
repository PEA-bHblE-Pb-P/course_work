<template>
  <h1 class="font-rock-salt text-2xl">Character id: {{ characterId }}</h1>
  <Location :location="location"/>
</template>

<script>
import Location from "./Location.vue";

export default {
  name: "Profile",
  components: {Location},
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

<style scoped>

</style>