<script setup lang="ts">
import { getImages, images, concatURL}  from '../http-api'


defineProps<{ msg: string }>()

getImages();


</script>

<template>
  <h1>{{ msg }}</h1>
  <img v-for="image in images" :src="concatURL(image.id)">

</template>


<style scoped>
.read-the-docs {
  color: #888;
}
</style>
