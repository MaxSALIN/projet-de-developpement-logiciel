<script setup lang="ts">
import { ref } from 'vue'
import { images, downloadImage, handleFileUpload, submitFile}  from '../http-api'

defineProps<{ msg: string }>()

const count = ref(0)
const titleClass = ref('title') 
const namesClass = ref('names')

</script>

<template>
  <h1 :class="titleClass"> {{ msg }}</h1>
  <h2 :class="namesClass"> Clément SIMON, Loïc THIN, Maxence SALIN</h2>
  <div class="card">
  <p> Voici un bouton pour vous amuser</p>
    <button type="button" @click="count++">Le compte est de : {{ count }}</button>
  </div>
  <p>
    <select>
      <option v-for="image in images" @click=downloadImage(image)> {{ image.name }}</option>
      
    </select>
    
  </p>
  


	<div class="container">
		<div>
			<h1 :class="titleClass">Déposez votre image ici</h1>
			<hr/>
			<label>File
				<input type="file" @change="handleFileUpload( $event )"/>
			</label>
			<br>
			<button v-on:click="submitFile()">Submit</button>
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
</style>
