import { createPinia } from "pinia";
import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router/index.js";
import store from "./stores/Store.js";
import SvgIcon from "vue3-icon";

createApp(App)
  .use(router)
  .use(createPinia())
  .use(store)
  .component("svg-icon", SvgIcon)
  .mount("#app");
