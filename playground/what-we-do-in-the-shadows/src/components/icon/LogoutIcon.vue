<template>
  <svg-icon type="mdi" :path="mdiLogout" :size="48"/>
</template>

<script>
import { mdiLogout } from '@mdi/js'

export default {
  name: "LogoutIcon",
  setup() {
    return {
      mdiLogout
    }
  }
}
</script>