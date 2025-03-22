<script setup lang="ts">
import { watch, ref } from 'vue'
import { imageID, getSimilarImages, concatURL, similarImages, imageSize, imageType, imageName }  from '../http-api'
import { useRoute } from 'vue-router'

const path = "/images/" + useRoute().params.id
const buttonImage = ref('buttonCenterImage') 

const route = useRoute();

watch(
  ()=> route.params.id,
  (newId, oldId)=>{
    
  }
)

</script>

<template>
           <router-link to="/">Back Home</router-link> 
      <p> Name : {{ imageName }}</p>
      <img id="image" :src="path">  
        <p> ID:  {{ imageID }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>

          <label for="number">Enter the number of images you would like to see: </label>
          <input type="number" name="number">

          <label for="descriptor">Enter the descriptor you would like to use: </label>
          <div>
          <input type="radio" id="des1" name="descriptor" value="RGB">
          <label for="des1">RGB</label>
          <input type="radio" id="des2" name="descriptor" value="HS">
          <label for="des2">Hue and Saturation</label>
          </div>

        <RouterLink :to="/images/+imageID+'/similar'"> <input type="submit" value="More images!" @click="getSimilarImages()"/></RouterLink> 
        </hr>


<RouterView></RouterView>

</template>

<style scoped>
.read-the-docs {
    color: #888;
  }
  .title {
    color: coral;
  }
  .names {
    color:gold;
  }
  
  .buttonCenterImage{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
  }
  #divImg {
    display:inline-flex;
    width: 22em;
    height: 22em;
  }
  
  #img {
    object-fit: contain;
    width: 18em;
    height: 18em;
    will-change: filter;
    transition: filter 300ms;
  }
  
  #img:hover {
      filter: drop-shadow(0 0 1em #4b51b6aa);
  }
</style>