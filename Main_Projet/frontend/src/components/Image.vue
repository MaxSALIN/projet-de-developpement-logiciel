<script setup lang="ts">
import { watch, ref } from 'vue'
import { images, downloadImage, handleFileUpload, submitFile, saveFile, ok, imageID, imageSize, imageType, imageName }  from '../http-api'


</script>

<template>
  Back Home
      <p> Name : {{ imageName }}</p>
      <img id="image" src="/images/"+$route.params.id>  
        <p> ID:  {{ imageID }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>
        <form action="" method="get" name="similar">
          <label for="number">Enter the number of images you would like to see: </label>
          <input type="number" name="number">

          <label for="descriptor">Enter the descriptor you would like to use: </label>
          <div>
          <input type="radio" id="des1" name="descriptor" value="RGB">
          <label for="des1">RGB</label>
          <input type="radio" id="des2" name="descriptor" value="HS">
          <label for="des2">Hue and Saturation</label>
          </div>

          <input type="submit" value="More images!"/>
        </form>
</template>