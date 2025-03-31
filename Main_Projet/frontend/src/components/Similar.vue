<script setup lang="ts">
import { ref } from 'vue'
import { downloadImage, concatURL, similarImages, getSimilarImages }  from '../http-api'


const buttonImage = ref('buttonCenterImage') 

</script>

<template>

<hr/>

          <label for="number">Enter the number of images you would like to see: </label>
          <input type="number" name="number" id="number">
          <div>
          <label for="descriptor">Enter the descriptor you would like to use: </label>

          <input type="radio" id="des1" name="descriptor" value="RGB">
          <label for="des1">RGB</label>
          <input type="radio" id="des2" name="descriptor" value="HS">
          <label for="des2">Hue and Saturation</label>
          </div>

         <input type="submit" value="More images!" @click="getSimilarImages()"/>

<div id="divImg" v-for="simage in similarImages" :class="buttonImage">
    <router-link :to="/image/+simage.id" @click="downloadImage(simage)"> <img id="img" :src="concatURL(simage.id)" ></router-link>
  <p>Similarité de l'image: {{ simage.similarité }}</p>
</div>
</template>