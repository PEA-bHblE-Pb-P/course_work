import { createRouter, createWebHistory } from "vue-router";
import Index from "../components/Index.vue";
import Typography from "../components/Typography.vue";
import Login from "../components/Login.vue";

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
      component: Typography
    },
    {
      path: "/login",
      name: "Login",
      component: Login
    }
  ],
});

export default router;
