<script setup lang="ts">
import { useRoute } from 'vue-router'

const path = "/images/" + useRoute().params.id



const imageName = localStorage.getItem("name");
const imageSize = localStorage.getItem("size");
const imageType = localStorage.getItem("type");


</script>

<template>
           <router-link to="/">Back Home</router-link> 
      <p> Name : {{ imageName }}</p>
      <img id="image" :src="path">  
        <p> ID:  {{ useRoute().params.id }}</p>
        <p> Size: {{ imageSize }}</p>
        <p> Type: {{ imageType }}</p>


           <router-link :to="'/image/'+useRoute().params.id+'/similar'"> <button type="button" >Similar Images</button></router-link>   <router-link :to="'/image/'+useRoute().params.id+'/modify'"> <button type="button" >Modify</button></router-link>

        </hr>


<RouterView></RouterView>

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