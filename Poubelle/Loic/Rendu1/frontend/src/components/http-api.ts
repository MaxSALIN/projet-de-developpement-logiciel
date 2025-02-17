import axios from 'axios'

// Fonction pour récupérer la liste des images
export function getImages() {
    return axios.get('/images') // Remplacez par l'URL réelle de votre backend
}

// Fonction pour récupérer une image par son ID
export function getImageById(id: number) {
    return axios.get(`/images/${id}`, { responseType: 'blob' }) // Remplacez par l'URL réelle de votre backend
}