<template>
  <div class="grid place-items-center">
    <form @submit.prevent="onSubmit">
      <div class="mt-10 mb-6">
        <input
          v-model="id"
          type="text"
          class="form-control block w-full px-4 py-2 text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
          placeholder="Character id"
        />
      </div>
      <div class="flex justify-between items-center mb-6">
        <div class="form-group form-check">
          <input
            id="exampleCheck3"
            type="checkbox"
            class="form-check-input appearance-none h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-black checked:bg-black focus:outline-none transition duration-200 mt-1 align-top bg-no-repeat bg-center bg-contain float-left mr-2 cursor-pointer"
            checked
          />
          <label
            class="form-check-label inline-block text-gray-800"
            for="exampleCheck2"
          >
            Remember me
          </label>
        </div>
      </div>
      <button
        class="inline-block px-7 py-3 bg-gray-500 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-gray-600 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
        type="submit"
        @click="loginAndSet"
      >
        LOGIN
      </button>
    </form>
    <div class="m-2 flex flex-wrap">
      <Character
        v-for="character in limitedCharacters"
        :key="character.id"
        :character="character"
        :highlight="id === character.id"
        @click="id = character.id"
      />
    </div>
  </div>
</template>

<script>
import { characters, login } from "../../api.js";
import router from "../../router/index.js";
import Character from "./Character.vue";

export default {
  name: "Login",
  components: { Character },
  data() {
    return {
      id: "",
      characters: [],
    };
  },
  computed: {
    limitedCharacters() {
      return this.characters.sort((a, b) => a.id - b.id).slice(0, 20);
    },
  },
  async beforeMount() {
    this.characters = await characters();
  },
  methods: {
    async loginAndSet() {
      await login(this.id).then(() => {
        this.$store.commit("setId", this.id);
        if (router.currentRoute.value.redirectedFrom === undefined)
          router.push("/profile");
        else router.push(router.currentRoute.value.redirectedFrom);
      });
    },
  },
};
</script>
