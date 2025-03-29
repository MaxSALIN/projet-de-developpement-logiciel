import axios from 'axios';
import { ref } from 'vue';
import router from './router';

const images = ref();
const similarImages = ref();

function getImages() {
  return axios.get('/images').then(function (response) {
    console.log(response);

    images.value = response.data;
    console.log(images.value);
  })
}

const numberref = ref();
const descrref = ref();



function getSimilarImages() {
  var descr = "";
  var number = (<HTMLInputElement>document.getElementById("number")).value;
  if ((<HTMLInputElement>document.getElementById("des1"))?.checked) {
    descr = (<HTMLInputElement>document.getElementById("des1"))?.value;
  } else {
    descr = (<HTMLInputElement>document.getElementById("des2"))?.value;
  }
  numberref.value = number.toString();
  descrref.value = descr;
  return axios.get('/images/' + localStorage.getItem("id") + "/similar?number=" + numberref.value + "&descriptor=" + descrref.value)
    .then(function (response) {
      console.log(response);
      similarImages.value = response.data;
      console.log(similarImages.value);
      ok.value = 1;
      console.log(similarImages.value);
      localStorage.setItem("simImages", similarImages.value);
      router.push({ name: "similar", params: { id: localStorage.getItem("id") } })
    }).catch(function (e) {
      console.log(e);
    });
}

const ok = ref(0);

const image_real = ref();
const imageID = ref();
const imageSize = ref();
const imageType = ref();
const imageName = ref();

function downloadImage(image: any) {
  axios.get('/images/' + image.id, { responseType: "blob" })
    .then(function (response) {
      const reader = new window.FileReader();
      reader.readAsDataURL(response.data);

      reader.onload = function () {
        const imageDataUrl = (reader.result as string);
        var imageEl = document.getElementById("image");

        if (imageEl == null) {
          throw new Error("imageEL null");

        } else {
          imageEl.setAttribute("src", imageDataUrl);
          image_real.value = image;
          imageID.value = image.id;
          imageSize.value = image.size;
          imageType.value = image.type; // essayer de mieux presenter ?
          imageName.value = image.name; // de meme ?
          ok.value = 1;
        }
      }
    });
  localStorage.setItem("name", image.name);
  localStorage.setItem("id", image.id);
  localStorage.setItem("size", image.size);
  localStorage.setItem("type", image.type);



}

function deleteImage(id: number) {
  axios.delete('/images/' + (id),
    {
      headers: {
        Authorization: 'authorizationToken'
      }
    }
  ).then(
    function (response) {
      getImages();
      ok.value = 0;
      var imageEl = document.getElementById("image");
      imageEl?.setAttribute("src", "");

    }
  );
}

function concatURL(imageId: any) {
  return "/images/".concat(imageId)
}


let file = ''
function handleFileUpload(event: any) {
  file = event.target.files[0];

}


function submitFile() {
  let formData = new FormData();

  formData.append('file', file);

  axios.post('/images',
    formData,
    {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }
  ).then(function () {
    console.log('SUCCESS!!');
    getImages();
  })
    .catch(function () {
      console.log('FAILURE!!');
    });
}

function saveFile(image: any) {
  axios.get('/images/' + image.id, { responseType: "blob" })
    .then(function (response) {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const a = document.createElement("a");
      a.href = url;
      a.download = image.name
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    })
    .catch(function (error) {
      console.error("Error saving the file:", error);
    });
}



export { images, similarImages, getImages, getSimilarImages, downloadImage, concatURL, handleFileUpload, submitFile, saveFile, ok, imageID, imageSize, imageType, imageName, deleteImage, image_real }