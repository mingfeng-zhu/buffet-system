import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes:[
        // 登录页
        {
            path:'/login',
            name:'login',
            component: () => import('../views/personality/login.vue'),
        },
        // 通过手机号登录
        {
            path:'/loginByTel',
            name:'loginByTel',
            component: () => import('../views/personality/loginByTel.vue'),
        },
        // 注册
        {
            path: '/register',
            name:'register',
            component:() => import('../views/personality/register.vue')
        },
        // 个人信息
        {
            path: '/personInfo',
            name:'personInfo',
            component:() => import('../views/tab/personInfo.vue')
        },
        // 修改个人信息
        {
            path: '/changeInfo',
            name:'changeInfo',
            component:() => import('../views/tab/changeInfo.vue')
        },
        // 修改密码
        {
            path: '/changePassword',
            name:'changeInfo',
            component:() => import('../views/personality/changePassword.vue')
        },
        // 总体布局
        {
            path: '/',
            name: 'layOut',
            component: () => import('../views/home/layOut.vue'),
            // redirect: store.getters['menuPath'],
            children: [
                // 首页
                {
                    path: '/',
                    name:'home',
                    component:() => import('../views/tab/home.vue')
                },
                // 购物车
                {
                    path: '/cart',
                    name:'cart',
                    component:() => import('../views/tab/cart.vue')
                },
                // 订单确认
                {
                    path: '/orderconfirm',
                    name:'orderconfirm',
                    component:() => import('../views/tab/orderconfirm.vue'),
                    meta:{
                        keepAlive:true
                    }
                },
                // 订单支付
                {
                    path: '/pay',
                    name:'pay',
                    component:() => import('../views/tab/pay.vue')
                },
                // 支付成功
                {
                    path: '/success',
                    name:'paysuccess',
                    component:() => import('../views/tab/success.vue')
                },
                // 支付失败
                {
                    path: '/faild',
                    name:'paufaild',
                    component:() => import('../views/tab/faild.vue')
                },
                // 搜索
                {
                    path: '/search',
                    name:'search',
                    component:() => import('../views/tab/search.vue')
                },
                // 个人中心
                {
                    path: '/person',
                    name:'person',
                    component:() => import('../views/tab/person.vue')
                },
                // 待支付订单
                {
                    path:  '/unpaid',
                    name: 'unpaid',
                    component:() => import('../views/tab/unpaid.vue')
                },
                // 未收货
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
                // 所有订单
                {
                    path:  '/allorder',
                    name: 'allorder',
                    component:() => import('../views/tab/allorder.vue')
                },
                // 地址
                {
                    path: '/address',
                    name:'address',
                    component:() => import('../views/tab/address.vue')
                },{
                // 添加地址
                    path: '/addressform',
                    name:'addressform',
                    component:() => import('../views/tab/addressform.vue')
                }, {
                // 地址详情
                    path: '/detail',
                    name:'detail',
                    component:() => import('../views/tab/detail.vue')
                },{
                // 编辑地址
                    path: '/editAddress',
                    name:'editAddress',
                    component:() => import('../views/tab/editAddress.vue')
                },
                {
                    // 订单详情
                    path: '/orderdetail',
                    name:'orderdetail',
                    component:() => import('../views/tab/orderdetail.vue')
                },
            ]
        }
    ]
})
export default router
