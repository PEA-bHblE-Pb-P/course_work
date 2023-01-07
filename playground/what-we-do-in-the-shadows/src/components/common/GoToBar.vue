<template>
  <h1 class="text-4xl font-bold">Pick a BAR to go</h1>
  <div class="grid grid-cols-3 gap-4">
    <Location
        v-for="loc in locations"
        :key="loc.id"
        :location="loc"
        @click="this.selected = loc"
        :class="{ 'bg-gray-400': loc.id === selected.id }"
    />
  </div>
  <button
      class="inline-block m-10 px-7 py-3 bg-gray-500 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-gray-600 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
      type="button"
      @click="go"
      :class="{ hidden: selected.id === undefined }"
  >
    GO
  </button>
</template>

<script>
import Location from "./Location.vue";
import { useLocationStore } from "../../stores/LocationStore.js";
import router from "../../router/index.js";
import {go_to_location_id} from "../../api.js";

export default {
  name: "GoToBar",
  components: { Location },
  data() {
    return {
      selected: {
        id: undefined
      },
    };
  },
  computed: {
    locations() {
      return useLocationStore().typedLocations.filter((loc)=>loc.type === 'bar');
    },
  },
  methods: {
    go() {
      console.log(this.selected);
      this.$store.commit("setLocation", this.selected);
      go_to_location_id(this.selected.id);
      router.push("/profile");
    },
  },
};
</script>

<style scoped></style>
