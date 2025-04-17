import { ElMessage } from 'element-plus'
import { createPinia, defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode'
import { reqLogin, reqLogout, reqUserInfo } from '@/api/useApi'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
export const useUserStore = defineStore('user', {
    state: () => ({
        token: '',
        userId:'',
        avatar:'',
        userInfo:{},
        storeId:'1'
    }),
    getters: {
        
    },
    
    actions: {
        async login(data:any){
            // console.log(258);
            let result1 = await reqLogin(data)
            if(result1.data.code == 200){
                // console.log(result1.data.data);
                this.token = result1.data.data
                this.userId = (jwtDecode(this.token).sub as string)
                localStorage.setItem('token',this.token)
                
                // let result2 = await reqUserInfo(this.userId)
                // if(result2.data.code == 200){
                //     this.userInfo = result2.data.data
                //     this.avatar = this.userInfo.img
                // }else{
                //     ElMessage({
                //         message:'获取用户信息出错',
                //         type:'error',
                //         plain:true
                //     })
                // }
            }else{
                ElMessage({
                    message:'登录出错',
                    type:'error',
                    plain:true
                })
            }
            
        },
        async logout(){
            // console.log(123);
            let result = await reqLogout(this.userId)
            if(result.data.code == 200){
                localStorage.removeItem('token')
                this.userId = ''
                ElMessage({
                    message:'退出成功',
                    type:'success',
                    plain:true
                })
               return true
            }else{
                ElMessage({
                    message:'退出失败',
                    type:'error',
                    plain:true
                })
                return false
            }
        }
    },
    persist:true
})

export default pinia


