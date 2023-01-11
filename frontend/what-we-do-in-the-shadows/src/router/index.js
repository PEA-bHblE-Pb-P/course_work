import { createRouter, createWebHistory } from "vue-router";
import Index from "../components/common/Index.vue";
import Typography from "../components/common/Typography.vue";
import Login from "../components/common/Login.vue";
import Logout from "../components/common/Logout.vue";
import GoTo from "../components/common/GoTo.vue";
import Profile from "../components/common/Profile.vue";
import GoToBar from "../components/common/GoToBar.vue";
import PeopleNearBy from "../components/common/PeopleNearBy.vue";
import store from "../stores/Store.js";

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
      meta: { requiresAuth: true },
    },
    {
      path: "/goto",
      name: "GoTo",
      component: GoTo,
      meta: { requiresAuth: true },
    },
    {
      path: "/bar",
      name: "GoToBar",
      component: GoToBar,
      meta: { requiresAuth: true },
    },
    {
      path: "/nearby",
      name: "PeopleNearby",
      component: PeopleNearBy,
      meta: { requiresAuth: true },
    },
    {
      path: "/profile",
      name: "Profile",
      component: Profile,
      meta: { requiresAuth: true },
    },
  ],
});

router.beforeEach((to, from, next) => {
  const isLogin = store.getters.isLogin;
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  if (requiresAuth && !isLogin) {
    const loginpath = window.location.pathname;
    next({ name: "Login", query: { from: loginpath } });
  } else next();
});

export default router;
