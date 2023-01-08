<template>
  <PageHeader>People nearby</PageHeader>
  <div :class="{ hidden: !isBar || !selected }" class="m-2">
    <button
      class="inline-block m-10 px-7 py-3 bg-gray-500 text-white font-medium text-sm leading-snug rounded shadow-md hover:bg-gray-600 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
      type="button"
      @click="drinkBlood"
    >
      DRINK BLOOD
    </button>
    <span class="font-medium block"
      >Blood amount:
      <input
        v-model.trim="amount"
        maxlength="3"
        class="mb-4 shadow-md text-center min-w-fit border-4 border-gray-300 text-gray-900 focus:outline-none focus:ring-gray-500 focus:border-gray-500 font-medium font-bold text-lg rounded-md"
    /></span>
  </div>
  <div class="flex flex-wrap">
    <Character
      v-for="person in people"
      :key="person.id"
      :character="person"
      :highlight="person.id === selected"
      @click="selectForDrinkBlood(person.id)"
    />
  </div>
</template>

<script>
import { character, drink, people_nearby } from "../../api.js";
import Character from "./Character.vue";
import PageHeader from "../layout/PageHeader.vue";
import { mapLocationType } from "../../mapper.js";

export default {
  name: "PeopleNearBy",
  components: { PageHeader, Character },
  data() {
    return {
      people: [],
      isBar: false,
      selected: undefined,
      amount: 0,
    };
  },
  async mounted() {
    if (this.$store.getters.shouldSetCharacter) {
      let char = await character(this.characterId);
      this.$store.commit("setCharacter", char);
    }

    this.isBar =
      mapLocationType(this.$store.state.character.location) === "bar";
    this.people = await people_nearby();
  },
  methods: {
    selectForDrinkBlood(id) {
      if (this.isBar) this.selected = id;
    },
    async drinkBlood() {
      await drink(this.selected, this.amount);
    },
  },
};
</script>
