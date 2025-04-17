<template>
    <div class="main">
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>选择分类</span>
                </div>
            </template>
            <div class="categoryType">
                <el-form ref="form" :model="goodCategories" label-width="80px">
                    <el-form-item label="商品分类">
                        <el-tag style="margin-right: 10px;" type="success" v-for="item in tagList" :key="item.id">{{ item.name }}</el-tag>
                        <el-button type="primary" @click="dialogVisible = true">添加分类</el-button>
                    </el-form-item>
                </el-form>
            </div>
            
        </el-card>
        <el-dialog v-model="dialogVisible" title="选择分类" width="500">
            <div class="setcategory">
                <div class="setcategory1">
                    <span>一级分类：</span>
                    <el-select  @change=getSecondCategory(firstCategoryId) v-model="firstCategoryId" 
                        placeholder="Select"
                        size="large" style="width: 240px">
                        <el-option v-for="item in firstCategoryList" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </div>
                <div class="setcategory2">
                    <span>二级分类：</span>
                    <el-select @change="getSecondCategory(secondCategoryId)" v-model="secondCategoryId"
                        placeholder="Select" size="large" style="width: 240px">
                        <el-option v-for="item in secondCategoryList" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </div>
                <div class="setcategory3">
                    <span>三级分类：</span>
                    <el-select  v-model="thirdCategoryId" placeholder="Select"
                        size="large" style="width: 240px">
                        <el-option v-for="item in secondCategoryList" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </div>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="cancel">取消</el-button>
                    <el-button type="primary" @click="addTagCategories">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts" name="AddGoods">
import { ref,onMounted } from 'vue';
import { reqFirstCategory,reqSecondCategory } from '@/api/goodsApi';
import { useAddGoodsStore } from '@/store/goods/addGoods';
let addGoodsStore = useAddGoodsStore()
let dialogVisible = ref(false)
let goodCategories = ref({
    categories: []
})
let firstCategoryList = ref([])
onMounted(async () => {
    let result = await reqFirstCategory('1')
    // console.log(result.data);
    if(result.data.code == 200){
        firstCategoryList.value = result.data.data
    }
})
let firstCategoryId = ref()
let secondCategoryId = ref()
let secondCategoryList = ref([])
const getSecondCategory = async(id:number)=>{
    let result = await reqSecondCategory("1",id)
    // console.log(result.data);
    if(result.data.code == 200){
        secondCategoryList.value = result.data.data
    }
}
let thirdCategoryId = ref()
let tagList = ref([])
const addTagCategories = ()=>{
    secondCategoryList.value.forEach(element => {
        if(element.id == thirdCategoryId.value){
            tagList.value.push(element)
        }
    });
    addGoodsStore.addGoodsDto.category3_id = thirdCategoryId.value
    clearData()
    dialogVisible.value = false
}
let showThirdSelect = (option:any)=>{
    console.log(option);
}
let cancel = ()=>{
    clearData()
    dialogVisible.value = false
}
let clearData = ()=>{
    firstCategoryId.value = ''
    secondCategoryId.value = ''
    thirdCategoryId.value = ''

}
</script>

<style scoped lang="scss"></style>