<script setup lang="ts">
import { ref } from 'vue';
import { getImages, images, concatURL, saveFile}  from '../http-api'


defineProps<{ msg: string }>()
const titleClass = ref('title') 
const buttonImage = ref('buttonCenterImage') 

getImages();


</script>

<template>
  <h1 :class="titleClass">{{ msg }}</h1>
  <div v-for="image in images" :class="buttonImage">
    <img :src="concatURL(image.id)" >
    <button type="button" @click="saveFile(image)"> Save File On Disk</button>
  </div>
  
</template>


<style scoped>
.read-the-docs {
  color: #888;
}
.title {
  color: coral;
}

.buttonCenterImage{
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

</style>
