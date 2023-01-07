import { createRouter, createWebHistory } from "vue-router";
import Index from "../components/common/Index.vue";
import Typography from "../components/common/Typography.vue";
import Login from "../components/common/Login.vue";
import Logout from "../components/common/Logout.vue";
import GoTo from "../components/common/GoTo.vue";
import Profile from "../components/common/Profile.vue";
import GoToBar from "../components/common/GoToBar.vue";
import PeopleNearBy from "../components/common/PeopleNearBy.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "Index",
      component: Index,
    },
    {
      path: "/typography",
      name: "Typography",
      component: Typography,
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
    },
    {
      path: "/logout",
      name: "Logout",
      component: Logout,
    },
    {
      path: "/goto",
      name: "GoTo",
      component: GoTo,
    },
    {
      path: "/bar",
      name: "GoToBar",
      component: GoToBar,
    },
    {
      path: "/nearby",
      name: "PeopleNearby",
      component: PeopleNearBy,
    },
    {
      path: "/profile",
      name: "Profile",
      component: Profile,
    }
  ],
});

export default router;
