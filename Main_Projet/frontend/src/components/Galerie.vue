<script setup lang="ts">
import HelloWorld from './HelloWorld.vue'
import A from './A.vue'
</script>

<template>
  <HelloWorld msg="Projet de développement Logiciel"/>
  <Submit msg="Postez vos images ici"/>
  <A msg="Bienvenue dans la galerie" />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
