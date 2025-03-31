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
const image = ref();


function getImage(id: string) {
  return axios.get('/images/' + id + '/metadata').then(function (response) {
    image.value = response.data;
    console.log(image.value);
  }).catch(function (e) {
    console.log(e);
  });
}

function getSimilarImages(id: string) {
  var descr = "";
  var number = (<HTMLInputElement>document.getElementById("number")).value;
  if ((<HTMLInputElement>document.getElementById("des1"))?.checked) {
    descr = (<HTMLInputElement>document.getElementById("des1"))?.value;
  } else {
    descr = (<HTMLInputElement>document.getElementById("des2"))?.value;
  }
  numberref.value = number.toString();
  descrref.value = descr;
  return axios.get('/images/' + id + "/similar?number=" + numberref.value + "&descriptor=" + descrref.value)
    .then(function (response) {
      similarImages.value = response.data;
      ok.value = 1;
      router.push({ name: "similar", params: { id: id } })
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
  axios.get('/images/' + image.id + '/content', { responseType: "blob" })
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
          ok.value = 1;

        }
      }
    });


}

function deleteImage(id: number) {
  axios.delete('/images/' + (id),
    {
      headers: {
        Authorization: 'authorizationToken'
      }
    }
  ).then(
    function () {
      getImages();
      ok.value = 0;
      var imageEl = document.getElementById("image");
      imageEl?.setAttribute("src", "");

    }
  );
}

function concatURL(imageId: any) {
  return "/images/".concat(imageId).concat("/content");
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



export { images, similarImages, getImages, getSimilarImages, downloadImage, concatURL, handleFileUpload, submitFile, saveFile, ok, imageID, imageSize, imageType, imageName, deleteImage, image_real, getImage, image }