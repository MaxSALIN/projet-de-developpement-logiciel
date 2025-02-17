import { ref } from 'vue'
import axios, { type AxiosResponse } from 'axios';
const images = ref();

function getImages() {
    return axios.get('/images').then(function (response) {
        // handle success
        console.log(response);
        images.value = response.data;
    });
}

getImages();


function getImage(url: string) {
    var imageEl = document.getElementById("0");
    var imageUrl = "/images/".concat(url);


    axios.get(imageUrl, { responseType: "blob" })
        .then(function (response: AxiosResponse) {
            const reader = new window.FileReader();
            reader.readAsDataURL(response.data);
            reader.onload = function () {
                const imageDataUrl = (reader.result as string);
                imageEl.setAttribute("src", imageDataUrl);
            }
        });
}


function getImageGallery(url: string) {
    var imageUrl = "/images/".concat(url);
    return imageUrl;
}

/*
  Defines the data used by the component
*/
let file = '';

/*
  Defines the method used by the component
*/

/*
  Adds a file
*/

/*
  Submits files to the server
*/
function submitFiles() {
    /*
      Initialize the form data
    */
    let formData = new FormData();

    /*
      Iteate over any file sent over appending the files
      to the form data.
    */

    formData.append('file', file);

    /*
      Make the request to the POST /select-files URL
    */
    axios.post('/images',
        formData,
        {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
    ).then(function () {
        console.log('SUCCESS!!');
    })
        .catch(function () {
            console.log('FAILURE!!');
        });
}

/*
  Handles the uploading of files
*/
function handleFilesUpload(event: any) {
    file = event.target.files[0];
}




export { getImages, getImage, getImageGallery, submitFiles, handleFilesUpload, images };