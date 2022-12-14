<template>
  <h1 class="text-4xl font-bold">Pick a place to go</h1>
  <Location
    v-for="loc in locations"
    :key="loc.id"
    :location="loc"
    @click="this.selected = loc.id"
    :class="{ 'bg-gray-400': loc.id === selected }"
  />
  <button
    class="inline-block m-10 px-7 py-3 bg-gray-500 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-gray-600 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
    type="button"
    @click="go"
    :class="{ hidden: selected === undefined }"
  >
    GO
  </button>
</template>

<script>
import Location from "./Location.vue";
import { useLocationStore } from "../../stores/LocationStore.js";
import router from "../../router/index.js";

export default {
  name: "GoTo",
  components: { Location },
  data() {
    return {
      locations: [],
      selected: undefined,
    };
  },
  computed: {
    locations() {
      return useLocationStore().typedLocations;
    },
  },
  methods: {
    go() {
      this.$store.commit("setLocation", this.selected);
      router.push("/");
    },
  },
};
</script>

<style scoped></style>
