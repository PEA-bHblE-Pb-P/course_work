<template>
  <svg-icon type="mdi" :path="mdiMap" :size="48"/>
</template>

<script>
import { mdiMap } from '@mdi/js'

export default {
  name: "LocationIcon",
  setup() {
    return {
      mdiMap
    }
  }
}
</script>