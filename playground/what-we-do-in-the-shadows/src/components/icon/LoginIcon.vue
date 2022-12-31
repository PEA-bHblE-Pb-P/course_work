<template>
  <svg-icon type="mdi" :path="mdiLogin" :size="48"/>
</template>

<script>
import { mdiLogin } from '@mdi/js'

export default {
  name: "LoginIcon",
  setup() {
    return {
      mdiLogin
    }
  }
}
</script>