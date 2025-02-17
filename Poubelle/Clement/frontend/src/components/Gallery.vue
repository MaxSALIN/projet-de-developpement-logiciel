<script setup lang="ts">
import { ref } from 'vue';
import {getImageGallery, getImages, submitFiles, handleFilesUpload, images } from '../http-api';




getImages();



</script>
<style scoped>
.read-the-docs {
  color: #888;
}

</style>

<template>
    <h1>Gallery</h1>
  <p>
  <img v-for="image in images" :src="getImageGallery(image.id)">
  </p>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label>Files
        <input type="file" v-on:change="handleFilesUpload($event)"/>
      </label>
      <button v-on:click="submitFiles()">Submit</button>
    </div>
    <br>
    <br>
  </div>
</template>

