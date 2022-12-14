import { createRouter, createWebHistory } from "vue-router";
import Index from "../components/common/Index.vue";
import Typography from "../components/common/Typography.vue";
import Login from "../components/common/Login.vue";
import Logout from "../components/common/Logout.vue";
import GoTo from "../components/common/GoTo.vue";

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
  ],
});

export default router;
