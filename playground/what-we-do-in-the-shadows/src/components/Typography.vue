<template>
  <div v-for="font in fonts" :key="font" class="shadow-md">
    <h1 :class="font" class="leading-tight text-8xl">{{font}}</h1>
    <h3 :class="font" class="leading-tight text-5xl">What We Do In The Shadows</h3>
    <h4 :class="font" class="leading-tight text-4xl">What We Do In The Shadows</h4>
    <h5 :class="font" class="leading-tight text-3xl">What We Do In The Shadows</h5>
  </div>
</template>

<script setup>
const fonts = ["font-righteous", "font-rock-salt"]
</script>
