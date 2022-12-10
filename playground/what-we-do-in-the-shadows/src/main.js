import { createPinia } from "pinia";
import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router/index.js";

createApp(App).use(router).use(createPinia()).mount("#app");
