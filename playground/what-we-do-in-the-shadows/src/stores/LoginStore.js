import { createStore } from "vuex";

const loginStore = createStore({
  state() {
    return {
      id: undefined,
    };
  },
  getters: {
    isLogin(state) {
      return state.id !== undefined;
    },
  },
  mutations: {
    setId(state, id) {
      state.id = id;
    },
  },
});

export default loginStore;
