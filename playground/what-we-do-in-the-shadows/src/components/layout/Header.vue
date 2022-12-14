<template>
  <header class="header">
    <nav class="relative flex w-full justify-between py-2 px-6 bg-gray-600">
      <router-link to="/" class="hover:bg-white p-2">Home</router-link>
      <router-link to="/typography" class="hover:bg-white p-2"
        >Typography</router-link
      >
      <router-link
        v-if="!isLogin"
        to="/login"
        class="float-right hover:bg-white p-2"
        >Login</router-link
      >
      <router-link
        v-if="isLogin"
        to="/goto"
        class="float-right hover:bg-white p-2"
        >Go somewhere</router-link
      >
      <router-link
        v-if="isLogin"
        to="/logout"
        class="float-right hover:bg-white p-2"
        >Logout</router-link
      >
    </nav>
  </header>
</template>
<script>
export default {
  computed: {
    isLogin() {
      return this.$store.getters.isLogin;
    },
  },
};
</script>
