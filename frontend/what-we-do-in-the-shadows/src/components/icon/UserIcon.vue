<template>
  <svg-icon type="mdi" :path="mdiAccount" :size="48" />
</template>

<script>
import { mdiAccount } from "@mdi/js";

export default {
  name: "UserIcon",
  setup() {
    return {
      mdiAccount,
    };
  },
};
</script>
