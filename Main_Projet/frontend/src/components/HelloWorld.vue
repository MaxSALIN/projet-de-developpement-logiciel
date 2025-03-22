<script setup lang="ts">
import { ref } from 'vue'
import { images, getSimilarImages, similarImages, downloadImage, concatURL, handleFileUpload, submitFile, saveFile, ok, imageID, imageSize, imageType, imageName}  from '../http-api'

defineProps<{ msg: string }>()

const count = ref(0)
const titleClass = ref('title') 
const namesClass = ref('names')
const buttonImage = ref('buttonCenterImage') 


</script>

<template>
  <h1 :class="titleClass"> {{ msg }}</h1>
  <h2 :class="namesClass"> Clément SIMON, Loïc THIN, Maxence SALIN</h2>
  <div class="card">
  <p> Voici un bouton pour vous amuser</p>
    <button type="button" @click="count++">Le compte est de : {{ count }}</button>
  </div>



      <hr/>
		<div>

			<h1 :class="titleClass">Déposez votre image ici</h1>
			<label>File
				<input type="file" @change="handleFileUpload( $event )"/>
			</label>
			<br>
			<button v-on:click="submitFile()">Submit</button>
      <hr/>
		</div>
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
