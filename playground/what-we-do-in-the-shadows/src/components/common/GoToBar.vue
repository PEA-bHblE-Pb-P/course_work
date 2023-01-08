<template>
  <PageHeader>Pick a BAR to go</PageHeader>
  <div class="grid grid-cols-3 gap-4">
    <Location
      v-for="loc in typedLocations"
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
import router from "../../router/index.js";
import {go_to_location_id, locations} from "../../api.js";
import PageHeader from "../layout/PageHeader.vue";

export default {
  name: "GoToBar",
  components: { PageHeader, Location },
  data() {
    return {
      selected: {
        id: undefined,
      },
      locations: []
    };
  },
  computed: {
    typedLocations() {
      return this.$store.getters.typedLocations.filter((loc)=>loc.type === "bar");
    }
  },
  methods: {
    async go() {
      this.$store.commit("setLocation", this.selected);
      await go_to_location_id(this.selected.id);
      this.$store.commit("setCharacter", undefined);
      await router.push("/profile");
    },
  },
  async beforeMount() {
    if (this.$store.getters.shouldSetLocations) {
      this.locations = await locations();
      await this.$store.commit("setLocations", this.locations)
    } else {
      this.locations = this.$store.state.locations;
    }
  }
};
</script>

<style scoped></style>
