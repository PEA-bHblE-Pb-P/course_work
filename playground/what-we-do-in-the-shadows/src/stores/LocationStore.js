import { defineStore } from "pinia";

export const useLocationStore = defineStore("locationStore", {
  state: () => ({
    locations: [
      {
        id: 1,
        lat: -41.2943296,
        lot: 174.7856846,
        name: "Genghis Khan House",
        type: 2,
        img: "",
      },
      {
        id: 2,
        lat: -41.2936503,
        lot: 174.8201893,
        name: "Chocolate Fish Cafe",
        type: 3,
        img: "",
      },
      {
        id: 3,
        lat: -41.2936419,
        lot: 174.7818361,
        name: "The Welsh Dragon Bar",
        type: 1,
        img: "",
      },
      {
        id: 14,
        lat: -41.2936443,
        lot: 174.735631,
        name: "Баня Эрика",
        img: "",
      },
    ],
  }),
});
