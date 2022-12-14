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
    <img
      class="mt-5"
      src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.denofgeek.com%2Fwp-content%2Fuploads%2F2019%2F05%2Fwhat-we-do-in-the-shadows-episode-6.jpg%3Fresize%3D768%252C432&f=1&nofb=1&ipt=397dbfbcd95a9beec72a0d77f7f66673bce2c743315b7ffd56ed83d731b9b1af&ipo=images"
    />
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
        router.back();
      } catch (e) {
        console.log(e);
      }
    },
  },
};
</script>

<style scoped></style>
