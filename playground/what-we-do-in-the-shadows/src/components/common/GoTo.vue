<template>
  <PageHeader>Pick a place to go</PageHeader>
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
  <template v-if="isHunter">
  <button
      class="inline-block m-10 px-7 py-3 bg-gray-600 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-gray-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
      type="button"
      @click="goForFight"
      :class="{ hidden: selected.id === undefined }"
  >
    GO FOR FIGHT
  </button>
  </template>
</template>

<script>
import Location from "./Location.vue";
import { useLocationStore } from "../../stores/LocationStore.js";
import router from "../../router/index.js";
import {character, go_for_fight, go_to_location_id} from "../../api.js";
import PageHeader from "../layout/PageHeader.vue";
import {isHunterType} from "../../mapper.js";

export default {
  name: "GoTo",
  components: { PageHeader, Location },
  data() {
    return {
      selected: {
        id: undefined,
      },
      isHunter: false
    };
  },
  computed: {
    locations() {
      return useLocationStore().typedLocations;
    },
    characterByState() {
      return this.$store.state.character;
    },
  },
  methods: {
    go() {
      this.$store.commit("setLocation", this.selected);
      go_to_location_id(this.selected.id);
      router.push("/profile");
    },
    goForFight() {
      this.$store.commit("setLocation", this.selected);
      go_for_fight(this.selected.id);
      router.push("/profile");
    }
  },
  async beforeMount() {
    if (this.$store.getters.shouldSetCharacter) {
      const char = await character(this.characterId);
      await this.$store.commit("setCharacter", char);
    }
    this.isHunter = isHunterType(this.characterByState.typeId);
  }
};
</script>

<style scoped></style>
