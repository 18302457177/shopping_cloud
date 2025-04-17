import router from '@/router/index'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({
    easing: 'ease', // 动画方式
    speed: 500, // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
  })
  
// 白名单
const whiteList = ['/login']
// 导航守卫
router.beforeEach((to, from, next) => {
    NProgress.start()
    if(whiteList.indexOf(to.path) !== -1){
        next()
    }else if(localStorage.getItem('token')!==null){
        next()
    }else{
        next('/login')
    }
})
router.afterEach(() => {
    // 在即将进入新的页面组件前，关闭掉进度条
    NProgress.done()
})