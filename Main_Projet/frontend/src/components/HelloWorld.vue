<script setup lang="ts">
import { ref } from 'vue'
import { images, similarImages, downloadImage, handleFileUpload, submitFile, concatURL, saveFile, ok, imageID, imageSize, imageType, imageName}  from '../http-api'

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
  <p :class="buttonImage">
    <select>
      <option>Choose an image</option>
      <option v-for="image in images" @click=downloadImage(image)> {{ image.name }}</option>

    </select>
    <div v-if="ok">  
      <p> Name : {{ imageName }}</p>
    </div>
      <img id="image" src="">  
<div v-if="ok">
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
    </div>

      <div id="divImg" v-for="image in similarImages" :class="buttonImage" v-if="ok">
        <img id="img" :src="concatURL(image.id)" >
        <p>{{ image.similarity }}</p>
  </div>

    
  </p>
  


	<div class="container">
		<div>
      <hr/>
			<h1 :class="titleClass">Déposez votre image ici</h1>
			<label>File
				<input type="file" @change="handleFileUpload( $event )"/>
			</label>
			<br>
			<button v-on:click="submitFile()">Submit</button>
      <hr/>
		</div>
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
</style>
