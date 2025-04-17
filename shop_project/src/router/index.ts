import { createWebHashHistory, createWebHistory, createRouter } from 'vue-router'
// import { useUserStore } from '@/store/user'
// let userStore = useUserStore()
const routes = [
    {
        name: '登录页面',
        path: '/login',
        component: () => import('@/pages/Login.vue')
    },
    {
        name: '注册页面',
        path: '/register',
        component: () => import('@/pages/Register.vue')
    },
    {
        name: '主页',
        path: '/home',
        component: () => import('@/pages/home/Home.vue')
    },
    {
        name: '订单页面',
        path: '/order',
        component: () => import('@/pages/orders/Order.vue')
    },
    {
        path: '/',
        redirect: '/login'
    },
    {
        name: '购物车',
        path: '/ShoppingCart',
        component: () => import('@/pages/ShoppingCart/ShoppingCart.vue')
    },
    // {
    //     path: '/NotFound',
    //     redirect: '/login'
    // },
    {
        name: 'detail',
        path: '/detail',
        component: () => import('@/pages/goods/Detail.vue')
    },
    {
        path: '/user',
        component: () => import('@/pages/User.vue'),
        children:[
            {
                name:'个人信息',
                path:'/userInfo',
                component:()=>import('@/pages/user/UserInfo.vue')
            },
            {
                name:'优惠卷',
                path:'/coupons',
                component:()=>import('@/pages/user/Coupons.vue')
            }
        ]
    },
    {
        name:'售后页面',
        path:'/afterMarket',
        component:()=>import('@/pages/afterMarket/AfterMarket.vue')
    },
    {
        name:'后台主页',
        path:'/background',
        component:()=>import('@/components/backend_business/Layout.vue'),
        children:[
            {
                name:'商家后台主页',
                path:'/homePage',
                component:()=>import('@/pages/backend_page/homePage.vue')
            },
            {
                name:'用户列表',
                path:'/userList',
                component:()=>import('@/pages/backend_page/user/UserList.vue')
            },
            {
                name:'角色列表',
                path:'/roleList',
                component:()=>import('@/pages/backend_page/auth/RoleList.vue')
            },
            {
                name:'权限列表',
                path:'/authList',
                component:()=>import('@/pages/backend_page/auth/AuthList.vue')
            },
            {
                name:'商品列表',
                path:'/goodsList',
                component:()=>import('@/pages/backend_page/goods/GoodsList.vue')
            },
            {
                name:'分类参数',
                path:'/categoryParameter',
                component:()=>import('@/pages/backend_page/goods/CategoryParameter.vue')
            },
            {
                name:'商品分类',
                path:'/goodsCategory',
                component:()=>import('@/pages/backend_page/goods/GoodsCategory.vue')
            },
            {
                name:'订单列表',
                path:'/orderList',
                component:()=>import('@/pages/backend_page/orders/OrderList.vue')
            },
        ]
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

// router.beforeEach((to,from,next)=>{
//     // if(routes. && userStore.userId != null && localStorage.getItem('token') != null){
//     //     next()
//     // }
// })


export default router