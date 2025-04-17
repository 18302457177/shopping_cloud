import { createApp } from 'vue'
import App from './App.vue'
import '@/reset/reset.scss'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from '@/router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import pinia from '@/store/user/user'
import 'virtual:svg-icons-register'
import gloalComponent from './components/index'
import './router/permission'
const app = createApp(App)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(gloalComponent)
app.use(pinia)
app.use(router)
app.mount('#app')

