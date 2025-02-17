<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import Galerie from './Galerie.vue'

defineProps<{ msg: string }>()

let images = ref([{id : 0, name : "test"}])

let file = ref('')

const count = ref(0)

axios.get('/images').then(function (response) {
    images.value = response.data;
  })

  
function get_image(id: any){
  axios.get('/images/' + id, { responseType:"blob" }).then(function (response) {
	const reader = new window.FileReader();
	reader.readAsDataURL(response.data); 
	reader.onload = function() {					
      var imageEl = document.getElementById("ba");
	    const imageDataUrl = (reader.result as string);
	    imageEl.setAttribute("src", imageDataUrl);
	  }
  })
}

function handleFileUpload(){
    file = $refs.file.files[0];
}

function submitFile(){
  let formData = new FormData();
  formData.append('file', file);
  axios.post( '/single-file',
  formData,
  {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
  }
  ).then(function(){
  console.log('SUCCESS!!');
  })
  .catch(function(){
  console.log('FAILURE!!');
  });
}

</script>

<template>
  <h1>{{ msg }}</h1>
  <select> 
    <option disabled value="">Choisissez</option>
    <option v-for="im in images" @click="get_image(im.id)"> {{ im.name }} </option>
  </select>

  <img id="ba" src="">

  <div class="card">
    <button type="button" @click="count++">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <Galerie />

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank">create-vue</a>, the official Vue + Vite
    starter
  </p>
  <p>
    Learn more about IDE Support for Vue in the
    <a href="https://vuejs.org/guide/scaling-up/tooling.html#ide-support" target="_blank">Vue Docs Scaling up Guide</a>.
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
