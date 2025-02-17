<script setup lang="ts">
import { ref, watch } from 'vue'
import axios from 'axios'

let images = ref([{id : 0, name : "test"}])

axios.get('/images').then(function (response) {
    images.value = response.data
  })

function get_image(id: any){
  axios.get('/images/' + id, { responseType:"blob" }).then(function (response) {
	const reader = new window.FileReader();
	reader.readAsDataURL(response.data); 
	reader.onload = function() {					
      var imageEl = document.getElementById(id);
	    const imageDataUrl = (reader.result as string);
	    imageEl.setAttribute("src", imageDataUrl);
	  }
  })
}

watch(images, () => {
  images.value.forEach(im => {
    get_image(im.id)  // Fetch each image by ID
  })
})

</script>

<template>

<li v-for="im in images"> <img style="width: 200px; height: auto;" :id=String(im.id) src=""> </li>

</template>