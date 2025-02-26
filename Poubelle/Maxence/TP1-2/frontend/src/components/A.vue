<script setup lang="ts">
import { ref } from 'vue';
import { getImages, images, concatURL}  from '../http-api'


defineProps<{ msg: string }>()
const titleClass = ref('title') 

getImages();


</script>

<template>
  <h1 :class="titleClass">{{ msg }}</h1>
  <img v-for="image in images" :src="concatURL(image.id)">

</template>


<style scoped>
.read-the-docs {
  color: #888;
}
.title {
  color: coral;
}
</style>
