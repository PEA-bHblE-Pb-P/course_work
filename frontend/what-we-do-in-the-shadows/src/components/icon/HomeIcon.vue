<template>
  <svg-icon type="mdi" :path="mdiHome" :size="48" />
</template>

<script>
import { mdiHome } from "@mdi/js";

export default {
  name: "HomeIcon",
  setup() {
    return {
      mdiHome,
    };
  },
};
</script>
