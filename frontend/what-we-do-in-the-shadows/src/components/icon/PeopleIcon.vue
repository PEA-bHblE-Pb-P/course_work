<template>
  <svg-icon type="mdi" :path="mdiAccountGroup" :size="48" />
</template>

<script>
import { mdiAccountGroup } from "@mdi/js";

export default {
  name: "PeopleIcon",
  setup() {
    return {
      mdiAccountGroup,
    };
  },
};
</script>
