import axios from 'axios'
import { ref } from 'vue';

const images = ref();

function getImages(){
    return axios.get('/images').then(function (response){
    console.log(response)
    images.value=response.data
  })
}


function downloadImage(image: any){
    axios.get('/images/'+image.id, { responseType:"blob" })
    .then(function (response) {
    const reader = new window.FileReader();
    reader.readAsDataURL(response.data); 
    
    reader.onload = function() {					
        const imageDataUrl = (reader.result as string);
        var imageEl = document.getElementById("image");

        if(imageEl==null){
          throw new Error("imageEL null");
          
        }else{
          imageEl.setAttribute("src", imageDataUrl);
        }
    }
  });
}

function concatURL(imageId :any){
    return "/images/".concat(imageId)
}


let file = ''
function handleFileUpload( event:any ){
	  file = event.target.files[0];
    
}

			
function submitFile(){
    let formData = new FormData();
    
    formData.append('file', file);
    
    axios.post( '/images',
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

function saveFile(image : any){
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



export { images, getImages, downloadImage, concatURL, handleFileUpload, submitFile, saveFile}