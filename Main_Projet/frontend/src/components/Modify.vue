<script setup lang="ts">
import { watch, ref } from 'vue'
import { imageID, concatURL, similarImages, imageSize, imageType, imageName }  from '../http-api'
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

        
</template>