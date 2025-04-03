<script setup lang="ts">
import { useRoute } from 'vue-router';
import { applyFilter, changeRadio }  from '../http-api'

let number = useRoute().params.id as string;


</script>

<template>

<hr/>

          
          <div>
          <label for="filter">Enter the filter you would like to use: </label>
          <p>
          <input type="radio" id="fil1" name="filter" value="Black_And_White" checked @click="changeRadio()">
          <label for="fil1">Black and white filter</label>
          <input type="radio" id="fil2" name="filter" value="Gaussian_Blur" @click="changeRadio()">
          <label for="fil2">Gaussian blur filter</label>
          <input type="radio" id="fil3" name="filter" value="Change_Color" @click="changeRadio()">
          <label for="fil3">Hue change filter</label>
          <input type="radio" id="fil4" name="filter" value="Pixel" @click="changeRadio()">
          <label for="fil4">Pixel filter</label>
        </p>
          </div>
          <p id="hue" style="display: none;">
            <label for="number">Enter the hue that you want (between 0 and 360) : </label>
            <input type="number" name="number" id="numberHue">
          </p>

          <p id="pixel" style="display: none;">
            <label for="number">Enter the number of pixel for the image blur : </label>
            <input type="number" name="number" id="numberPixel">
          </p>
          

         <input type="submit" value="Apply your filter NOW!" @click="applyFilter(number)"/>


</template>