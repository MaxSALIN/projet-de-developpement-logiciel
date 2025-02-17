<script setup lang="ts">
import { ref } from 'vue'
import { images, downloadImage, handleFileUpload, submitFile}  from '../http-api'

defineProps<{ msg: string }>()

const count = ref(0)



</script>

<template>
  <h1>{{ msg }}</h1>

  <div class="card">
    <button type="button" @click="count++">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>
  <p>
    <select>
      <option v-for="image in images" @click=downloadImage(image)> {{ image.name }}</option>
    </select>
  </p>
  <p><img id="image" src=""></p>
  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank"
      >create-vue</a
    >, the official Vue + Vite starter
  </p>
  <p>
    Learn more about IDE Support for Vue in the
    <a
      href="https://vuejs.org/guide/scaling-up/tooling.html#ide-support"
      target="_blank"
      >Vue Docs Scaling up Guide</a
    >.
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>


	<div class="container">
		<div>
			<h1>Déposez votre image ici</h1>
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
</style>
