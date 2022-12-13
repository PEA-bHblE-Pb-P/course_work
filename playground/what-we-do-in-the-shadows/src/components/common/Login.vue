<template>
  <div class="grid place-items-center">
    <form v-if="!this.$store.isLogin" @submit.prevent="onSubmit">
      <div class="mt-10 mb-6">
        <input
          type="text"
          class="form-control block w-full px-4 py-2 text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
          placeholder="Character id"
          v-model="id"
        />
      </div>
      <div class="flex justify-between items-center mb-6">
        <div class="form-group form-check">
          <input
            type="checkbox"
            class="form-check-input appearance-none h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-black checked:bg-black focus:outline-none transition duration-200 mt-1 align-top bg-no-repeat bg-center bg-contain float-left mr-2 cursor-pointer"
            id="exampleCheck3"
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
    <img
      class="mt-5"
      src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fnypost.com%2Fwp-content%2Fuploads%2Fsites%2F2%2F2020%2F05%2Fwhat-we-do-in-the-shadows-02.jpeg%3Fquality%3D90%26strip%3Dall&f=1&nofb=1&ipt=5bc07f3a63ce1a346c438bcf3640e9225f03a834b4950454df7c48cd6454ec94&ipo=images"
    />
  </div>
</template>

<script>
import {login} from "../../api.js";

export default {
  name: "Login",
  data() {
    return {
      id: ""
    }
  },
  mounted() {
    console.log(this.$store.isLogin)
  },
  methods: {
    loginAndSet() {
      try {
        login(this.id)
        this.$store.commit('setId', this.id)
        console.log(this.$store.id)
      } catch (e) {
        console.log(e)
      }
    }
  }
}
</script>
