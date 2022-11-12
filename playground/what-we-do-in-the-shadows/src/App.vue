<template>
  <main>
    <div class="course-work-welcome">
      <h2>РЕАЛЬНЫЕ УПЫРИ ЕПТА</h2>
      <div class="locations">
        <h3>ЛОКАЦИИ</h3>
        <Location
          v-for="location of locationStore.locations"
          :key="location.id"
          :location="location"
        />
      </div>
      <div class="location">
        <h3>ДОМА</h3>
        <Location
            v-for="location of locationStore.houses"
            :key="location.id"
            :location="location"
        />
      </div>
    </div>
  </main>
</template>

<script setup>
import Location from "./components/Location.vue";
import { useLocationStore } from "./stores/LocationStore.js";

const locationStore = useLocationStore();
</script>

<style scoped>
.course-work-welcome {
  background-image: url("src/assets/упыри.jpg");
  background-attachment: fixed;
  background-size: 60%;
  background-repeat: no-repeat;
  background-position: right;
  background-color: yellow;
}
</style>
