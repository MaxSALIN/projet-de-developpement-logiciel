<script setup lang="ts">
import { watch, ref } from 'vue'
import { imageID, concatURL, similarImages }  from '../http-api'
import { useRoute } from 'vue-router'

const path = "/images/" + useRoute().params.id
const buttonImage = ref('buttonCenterImage') 

const imageName = localStorage.getItem("name");
const imageSize = localStorage.getItem("size");
const imageType = localStorage.getItem("type");
</script>

<template>

<p> Name : {{ imageName }}</p>
      <img id="image" :src="path">  
        <p> ID:  {{ useRoute().params.id }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>

        On attends le code de Maxence
</template>