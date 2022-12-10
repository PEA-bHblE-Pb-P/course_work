import { createRouter, createWebHistory } from "vue-router";
import Index from "../components/Index.vue";
import Typography from "../components/Typography.vue";

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
    }
  ],
});

export default router;
