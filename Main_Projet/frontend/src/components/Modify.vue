<script setup lang="ts">
//import { ref } from 'vue'
import { useRoute } from 'vue-router';
import { applyFilter }  from '../http-api'

let number = useRoute().params.id as string;

//const buttonImage = ref('buttonCenterImage') 

</script>

<template>

<hr/>

          <label for="number">Enter the hue that you want (between 0 and 360 or the number of pixel for the pixeleted image): </label>
          <input type="number" name="number" id="number">
          <div>
          <label for="filter">Enter the filter you would like to use: </label>

          <input type="radio" id="fil1" name="filter" value="Black_And_White">
          <label for="fil1">Black and white filter</label>
          <input type="radio" id="fil2" name="filter" value="Gaussian_Blur">
          <label for="fil2">Gaussian blur filter</label>
          <input type="radio" id="fil3" name="filter" value="Change_Color">
          <label for="fil3">Hue change filter</label>
          <input type="radio" id="fil4" name="filter" value="Pixel">
          <label for="fil4">Pixel filter</label>
          </div>

         <input type="submit" value="Apply your filter NOW!" @click="applyFilter(number)"/>

<!--<div id="divImg" v-for="simage in similarImages" :class="buttonImage">
    <router-link :to="/image/+simage.id" @click="downloadImage(simage)"> <img id="img" :src="concatURL(simage.id)" ></router-link>
  <p>Similarité de l'image: {{ simage.similarité }}</p>
</div>-->
</template>