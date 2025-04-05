import { createWebHistory, createRouter } from "vue-router";
import type { RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: "home",
        component: () => import("./components/Galerie.vue"),
        props: true
    },
    {
        path: '/image/:id',
        name: "image",
        component: () => import("./components/Image.vue"),
        props: ({ params }) => ({ id: Number(params.id) || 0 }),
        children: [
            {
                path: 'similar',
                name: "similar",
                component: () => import("./components/Similar.vue")
            },
            {
                path: 'modify',
                name: "modify",
                component: () => import("./components/Modify.vue")
            }
        ]

    },
    {
        path: '/folder/:name',
        name: 'folder',
        component: () => import("./components/Folder.vue"),
        props: ({ params }) => ({ name: String(params.name) || 'images' })
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;