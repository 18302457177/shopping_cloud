import { defineStore,createPinia } from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
export const useGoodsStore = defineStore('goods',{
    state:()=>({
        goodsId:'',
    }),
    getters: {

    },
    actions: {

    },
    persist:true
})