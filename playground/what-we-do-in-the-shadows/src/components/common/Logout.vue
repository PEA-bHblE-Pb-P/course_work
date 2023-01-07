<template>
  <div class="grid place-items-center">
    <form @submit.prevent="onSubmit">
      <button
        class="inline-block m-10 px-7 py-3 bg-gray-500 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-gray-600 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
        type="submit"
        @click="logoutAndSet"
      >
        LOGOUT
      </button>
    </form>
  </div>
</template>

<script>
import { logout } from "../../api.js";
import router from "../../router/index.js";

export default {
  methods: {
    logoutAndSet() {
      try {
        logout();
        this.$store.commit("setId", undefined);
        router.push("/");
      } catch (e) {
        console.log(e);
      }
    },
  },
};
</script>

<style scoped></style>
