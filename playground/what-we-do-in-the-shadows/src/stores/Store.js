import { createStore } from "vuex";

const store = createStore({
  state() {
    return {
      id: undefined,
      character: undefined,
      location: undefined,
    };
  },
  getters: {
    isLogin(state) {
      return state.id !== undefined;
    },
    shouldSetCharacter(state) {
      return state.character === undefined && state.id !== undefined;
    }
  },
  mutations: {
    setId(state, id) {
      state.id = id;
    },
    setCharacter(state, character) {
      state.character = character
    },
    setLocation(state, location) {
      state.location = location;
    },
  },
});

export default store;
