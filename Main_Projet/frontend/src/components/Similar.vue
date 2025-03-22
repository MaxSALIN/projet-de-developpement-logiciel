<script setup lang="ts">
import { watch, ref } from 'vue'
import { imageID, downloadImage, concatURL, similarImages, imageSize, imageType, imageName }  from '../http-api'
import { useRoute } from 'vue-router'

const path = "/images/" + useRoute().params.id
const buttonImage = ref('buttonCenterImage') 


</script>

<template>

<router-link to="/">Back Home</router-link> 
      <p> Name : {{ imageName }}</p>
      <img id="image" :src="path">  
        <p> ID:  {{ imageID }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>



<div id="divImg" v-for="simage in similarImages" :class="buttonImage">
    <router-link :to="/images/+simage.id" @click="downloadImage(simage)"> <img id="img" :src="concatURL(simage.id)" ></router-link>
  <p>Similarité de l'image: {{ simage.similarité }}</p>
</div>
</template>