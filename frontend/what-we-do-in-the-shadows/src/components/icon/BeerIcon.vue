<template>
  <svg-icon type="mdi" :path="mdiBeer" :size="48" />
</template>

<script>
import { mdiBeer } from "@mdi/js";

export default {
  name: "BeerIcon",
  setup() {
    return {
      mdiBeer,
    };
  },
};
</script>
