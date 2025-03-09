import { createApp } from 'vue'
import { createWebHistory, createRouter } from 'vue-router'
import './style.css'
import App from './App.vue'
import Image from './components/Image.vue'

const routes = [
    { path: "/", component: App },
    { path: "/images/:id", component: Image },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

createApp(App).use(router).mount('#app')
