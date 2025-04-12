<script setup lang="ts">
import { ref } from 'vue';
import { getImages, images, directories, concatURL, saveFile, deleteImage, downloadImage, getDirectories }  from '../http-api'

defineProps<{ msg: string }>()
const titleClass = ref('title') 
const buttonImage = ref('buttonCenterImage') 

getImages();
getDirectories();

</script>

<template>
  <h1 :class="titleClass">{{ msg }}</h1>

  <pre></pre>
</hr>
</hr>
  <div id="block">
      <div id="divFolder" class="slide" v-for="folder in directories">
        <div id="insider">
        <router-link :to="/folder/+folder.name"><button type="button"> {{ folder.name }}</button></router-link>
        </div>
      </div>
  </div>

  <div id="divImg" v-for="image in images" :class="buttonImage">
      <router-link :to="/image/+image.id" @click="downloadImage(image)"><img id="img" :src="concatURL(image.id)" ></router-link> 
      <button type="button" @click="saveFile(image)"> Save File On Disk</button>
      <button type="button" @click="deleteImage(image.id)">Delete</button>
  </div>
  

</template>


<style scoped>
.read-the-docs {
  color: #888;
}
.title {
  color: coral;
}

#divImg {
  display:inline-flex;
  width: 20em;
  height: 20em;
}

#insider {
  display: block;
  align-items: center;
}
#divFolder {
  display: inline-flex;
  align-items: center;
  margin:1vmin;
}
#divFolder:hover {
  transform: scale(1.05);
}

#block {
  display: block;
  width: 100%;
  height: 7em;
  align-items: center;

}
#img {
  width: 18em;
  height: 18em;
  object-fit: contain;
  will-change: filter;
  transition: filter 300ms;
}


#img:hover {
    filter: drop-shadow(0 0 1em #4b51b6aa);
    transform: scale(1.1);
}

.buttonCenterImage{
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 5em;
}

</style>
