import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes:[
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
            path: '/personInfo',
            name:'personInfo',
            component:() => import('../views/tab/personInfo.vue')
        },
        {
            path: '/changeInfo',
            name:'changeInfo',
            component:() => import('../views/tab/changeInfo.vue')
        },
        {
            path: '/changePassword',
            name:'changeInfo',
            component:() => import('../views/personality/changePassword.vue')
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
                    path: '/orderconfirm',
                    name:'orderconfirm',
                    component:() => import('../views/tab/orderconfirm.vue'),
                    meta:{
                        keepAlive:true
                    }
                },
                {
                    path: '/pay',
                    name:'pay',
                    component:() => import('../views/tab/pay.vue')
                },
                {
                    path: '/success',
                    name:'paysuccess',
                    component:() => import('../views/tab/success.vue')
                },
                {
                    path: '/faild',
                    name:'paufaild',
                    component:() => import('../views/tab/faild.vue')
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
                },
                {
                    path:  '/unpaid',
                    name: 'unpaid',
                    component:() => import('../views/tab/unpaid.vue')
                },
                {
                    path:  '/unreceived',
                    name: 'unreceived',
                    component:() => import('../views/tab/unreceived.vue')
                },
                {
                    path:  '/unevaluated',
                    name: 'unevaluated',
                    component:() => import('../views/tab/unevaluated.vue')
                },
                {
                    path:  '/allorder',
                    name: 'allorder',
                    component:() => import('../views/tab/allorder.vue')
                },
                {
                    path: '/address',
                    name:'address',
                    component:() => import('../views/tab/address.vue')
                },{
                    path: '/addressform',
                    name:'addressform',
                    component:() => import('../views/tab/addressform.vue')
                }, {
                    path: '/detail',
                    name:'detail',
                    component:() => import('../views/tab/detail.vue')
                },{
                    path: '/editAddress',
                    name:'editAddress',
                    component:() => import('../views/tab/editAddress.vue')
                },
                {
                    path: '/orderdetail',
                    name:'orderdetail',
                    component:() => import('../views/tab/orderdetail.vue')
                },
            ]
        }
    ]
})
export default router
