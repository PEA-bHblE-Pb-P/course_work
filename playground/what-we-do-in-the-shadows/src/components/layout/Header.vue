<template>
  <div>
    <nav class="flex items-center justify-between flex-wrap p-6 bg-gray-600 text-white">
      <div class="flex items-center flex-shrink-0 text-white mr-6">
        <router-link to="/" class="hover:bg-gray-400 p-2">
          <HomeIcon />
        </router-link>
      </div>
      <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div class="lg:flex-grow text-sm">
          <router-link
              v-if="isLogin"
              to="/goto"
              class="block mt-4 hover:bg-gray-400 p-2"
          >
            <LocationIcon />
          </router-link>
          <router-link v-if="isLogin" to="to" class="block mt-4 hover:bg-gray-400 p-2">
            <PeopleIcon/>
          </router-link>
          <router-link v-if="isLogin" to="/profile" class="block mt-4 hover:bg-gray-400 p-2">
            <UserIcon />
          </router-link>
        </div>
        <div>
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
        </div>
      </div>
    </nav>
  </div>
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
