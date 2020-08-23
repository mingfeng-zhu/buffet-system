import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes:[
        {
            path:'/test',
            name:'home',
            component:() => import('../views/home/home.vue')
        },
        {
            path:'/login',
            name:'login',
            component: () => import('../views/personality/login.vue'),
        },
        {
            path:'/loginByTel',
            name:'loginByTel',
            component: () => import('../views/personality/loginByTel.vue'),
        },
        {
            path: '/register',
            name:'register',
            component:() => import('../views/personality/register.vue')
        },
        {
            path: '/',
            name: 'layOut',
            component: () => import('../views/home/layOut.vue'),
            // redirect: store.getters['menuPath'],
            children: [
                {
                    path: '/',
                    name:'home',
                    component:() => import('../views/tab/home.vue')
                },
                {
                    path: '/cart',
                    name:'cart',
                    component:() => import('../views/tab/cart.vue')
                },
                {
                    path: '/search',
                    name:'search',
                    component:() => import('../views/tab/search.vue')
                },
                {
                    path: '/person',
                    name:'person',
                    component:() => import('../views/tab/person.vue')
                },{
                    path: '/address',
                    name:'address',
                    component:() => import('../views/tab/address.vue')
                },{
                    path: '/addressform',
                    name:'addressform',
                    component:() => import('../views/tab/addressform.vue')
                },
            ]
        }
    ]
})
export default router
