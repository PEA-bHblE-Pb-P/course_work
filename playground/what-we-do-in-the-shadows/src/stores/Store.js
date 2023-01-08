import { createStore } from "vuex";
import { typedLocation } from "../mapper.js";

const store = createStore({
  state() {
    return {
      id: undefined,
      character: undefined,
      location: undefined,
      locations: undefined
    };
  },
  getters: {
    isLogin(state) {
      return state.id !== undefined;
    },
    shouldSetCharacter(state) {
      return state.character === undefined && state.id !== undefined;
    },
    shouldSetLocations(state) {
      return state.locations === undefined;
    },
    typedLocations(state) {
      return state.locations.map((loc) => typedLocation(loc));
    },
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
    setLocations(state, locations) {
      state.locations = locations;
    },
    unsetLocations(state) {
      state.location = undefined;
      state.locations = undefined;
    }
  },
});

export default store;
