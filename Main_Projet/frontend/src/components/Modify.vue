<script setup lang="ts">

import { useRoute } from 'vue-router'

const path = "/images/" + useRoute().params.id

const imageName = localStorage.getItem("name");
const imageSize = localStorage.getItem("size");
const imageType = localStorage.getItem("type");
</script>

<template>
<hr/>
<p> Name : {{ imageName }}</p>
      <img id="image" :src="path">  
        <p> ID:  {{ useRoute().params.id }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>

        On attends le code de Maxence
</template>