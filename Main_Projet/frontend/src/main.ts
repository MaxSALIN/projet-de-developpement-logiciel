import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import './style.css'
import App from './App.vue'
import Image from './components/Image.vue'
import Similar from './components/Similar.vue'
import Modify from './components/Modify.vue'


const routes = [
    { path: '/', component: App },
    {
        path: '/images/:id', component: Image,
        children: [
            {
                path: 'similar',
                component: Similar
            },
            {
                path: 'modify',
                component: Modify
            }
        ]
    },

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

createApp(App).use(router).mount('#app')
