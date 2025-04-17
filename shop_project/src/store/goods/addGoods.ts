import { defineStore } from "pinia";
import { reqAddGoods } from "@/api/goodsApi";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

export const useAddGoodsStore = defineStore('addGoods', {
    
    state: () => ({
        addGoodsDto: {
            category3_id: '',
            goodsData: {},
            skuList: [],
            imgMd5:'',
            imgSuffix:'',
            videoMd5:'',
            videoSuffix:''
        }
    }),
    getters: {

    },
    actions: {
        
        async addGoods(){
            let result = await reqAddGoods(this.addGoodsDto)
            console.log(result);
            if(result.data.code == 200){
                ElMessage({
                    message:'添加成功',
                    type:'success',
                    plain:true
                })
            }else{
                ElMessage({
                    message:'添加失败',
                    type:'error',
                    plain:true
                })
            }
        }
    }
})