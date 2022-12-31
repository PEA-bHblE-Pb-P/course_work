<template>
  <header class="header">
    <nav
      class="relative flex w-full py-2 px-6 bg-gray-600 text-white"
    >
      <router-link to="/" class="hover:bg-gray-400 p-2">
        <HomeIcon />
      </router-link>
      <router-link to="/typography" class="hover:bg-gray-400 p-2">
        <FontIcon />
      </router-link>
      <router-link
          v-if="isLogin"
          to="/goto"
          class="float-right hover:bg-gray-400 p-2"
      >
        <LocationIcon />
      </router-link>
      <PeopleIcon/>

      <router-link v-if="isLogin" to="/profile" class="hover:bg-gray-400 p-2">
        <UserIcon />
      </router-link>
      <router-link
        v-if="!isLogin"
        to="/login"
        class="float-right hover:bg-gray-400 p-2"
      >
        <LoginIcon />
      </router-link>
      <router-link
        v-if="isLogin"
        to="/logout"
        class="float-right hover:bg-gray-400 p-2"
      >
        <LogoutIcon />
      </router-link>
    </nav>
  </header>
</template>
<script>
import HomeIcon from "../icon/HomeIcon.vue";
import FontIcon from "../icon/FontIcon.vue";
import UserIcon from "../icon/UserIcon.vue";
import LogoutIcon from "../icon/LogoutIcon.vue";
import LocationIcon from "../icon/LocationIcon.vue";
import LoginIcon from "../icon/LoginIcon.vue";
import PeopleIcon from "../icon/PeopleIcon.vue";

export default {
  components: {
    PeopleIcon,
    LoginIcon,
    LocationIcon,
    LogoutIcon,
    UserIcon,
    FontIcon,
    HomeIcon,
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin;
    },
  },
};
</script>
