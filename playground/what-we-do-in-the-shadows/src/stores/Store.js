import { createStore } from "vuex";

const store = createStore({
  state() {
    return {
      id: undefined,
      location: undefined,
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
    setLocation(state, location) {
      state.location = location;
    },
  },
});

export default store;
