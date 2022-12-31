<template>
  <svg-icon type="mdi" :path="mdiFormatFont" :size="48"/>
</template>

<script>
import { mdiFormatFont } from '@mdi/js'

export default {
  name: "FontIcon",
  setup() {
    return {
      mdiFormatFont
    }
  }
}
</script>