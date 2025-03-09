import { computed, createApp } from 'vue'
import { useRoute, createWebHistory, createRouter } from 'vue-router'
import './style.css'
import App from './App.vue'
import Image from './components/Image.vue'
import HelloWorld from './components/HelloWorld.vue'



const route = useRoute()

const router = createRouter({
    history: createWebHistory(),
    routes:
        [

            { path: "/image/:id", component: Image, props: true },

        ],
})

export default {
    methods: {
        getSimilar(max: number, des: string) {
            router.push({ path: '/similar', query: { number: max, descriptor: des } })
        },
    },
}


createApp(App).use(router).mount('#app')
