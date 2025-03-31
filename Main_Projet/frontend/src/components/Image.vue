<script setup lang="ts">
import { useRoute } from 'vue-router'
import { getImage, image } from '../http-api';

let number = useRoute().params.id as string;

const path = "/images/" +number + "/content";


getImage(number);


</script>

<template>
           <router-link to="/">Back Home</router-link> 
      <p> Name : {{ image[0].name }}</p>
      <img id="image0" :src="path">  
        <p> ID:  {{ useRoute().params.id }}</p>
        <p> Size: {{ image[0].size }}</p>
        <p> Type: {{ image[0].type }}</p>


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