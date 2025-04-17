<template>
    <div class="main">
        <el-card>
            <div class="search">
                <span>一级分类：</span>
                <el-select @change=getSecondCategory(firstCategoryId) v-model="firstCategoryId" placeholder="Select" size="large" style="width: 240px">
                    <el-option v-for="item in firstCategoryList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
                <span>二级分类：</span>
                <el-select @change="getSecondCategory(secondCategoryId)" v-model="secondCategoryId" placeholder="Select" size="large" style="width: 240px">
                    <el-option v-for="item in secondCategoryList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
                <span>三级分类：</span>
                <el-select @change="getStaticAndDynamicCategory" v-model="thirdCategoryId" placeholder="Select" size="large" style="width: 240px">
                    <el-option v-for="item in secondCategoryList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </div>
            <div class="category">
                <el-button class="btn2" @click="type = false">静态参数</el-button>
                <el-button class="btn1" @click="type = true">动态参数</el-button>
                
            </div>
            <el-divider />
            <div class="list" v-show="type == false">
                <div class="addCategory">
                    <el-button style="margin-left: 30px;" type="primary">添加静态属性</el-button>
                </div>
                <div class="tableList">
                    <el-table :data="staticCategory" border>
                        <el-table-column type="index" width="50" />
                        <el-table-column prop="name" label="参数名称" />
                        <el-table-column prop="操作" label="操作" >
                            <template #="{row}" >
                                <el-button type="primary" icon="Edit"></el-button>
                                <el-button type="danger" icon="Delete"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div class="pagination">
                    <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize"
                        :page-sizes="[100, 200, 300, 400]"
                        layout="total, sizes, prev, pager, next, jumper" :total="400" @size-change="changePage"
                        @current-change="changePage" />
                </div>
            </div>
            <div class="list" v-show="type == true">
                <div class="addCategory">
                    <el-button style="margin-left: 30px;" type="primary">添加动态属性</el-button>
                </div>
                <div class="tableList">
                    <el-table :data="dynamicCategory" border>
                        <el-table-column type="index" width="50" />
                        <el-table-column prop="param" label="参数名称" width="200"/>
                        <el-table-column label="参数值" >
                            <template #="{row}">
                                <el-tag v-for="item in row.children" type="success">{{ item.param }}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="操作" label="操作" width="300">
                            <template #="{row}">
                                <el-button type="primary" icon="Edit"></el-button>
                                <el-button type="danger" icon="Delete"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div class="pagination">
                    <el-pagination v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                        :page-sizes="[100, 200, 300, 400]" :size="size" :disabled="disabled" :background="background"
                        layout="total, sizes, prev, pager, next, jumper" :total="400" @size-change="handleSizeChange"
                        @current-change="handleCurrentChange" />
                </div>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="CategoryParameter">
import { reqFirstCategory,reqSecondCategory,reqStaticCategories,reqDynamicCategories,
    
 } from '@/api/goodsApi';
import { onMounted,ref } from 'vue';
let type = ref(false)
let pageNo = ref(1)
let pageSize = ref(5)
let firstCategoryList = ref([])
onMounted(async () => {
    let result = await reqFirstCategory('1')
    // console.log(result.data);
    if(result.data.code == 200){
        firstCategoryList.value = result.data.data
    }
})
let firstCategoryId = ref()
let secondCategoryList = ref([])
let thirdCategoryId = ref()
const getSecondCategory = async(id:number)=>{
    let result = await reqSecondCategory("1",id)
    // console.log(result.data);
    if(result.data.code == 200){
        secondCategoryList.value = result.data.data
    }
}
let secondCategoryId = ref()
let staticCategory = ref([])
let dynamicCategory = ref([])
const getStaticAndDynamicCategory = async()=>{
    // console.log(thirdCategoryId.value);
    let result = await reqStaticCategories("1",thirdCategoryId.value)
    console.log(result.data);
    if(result.data.code == 200){
        staticCategory.value = result.data.data
    }
    let result1 = await reqDynamicCategories("1",thirdCategoryId.value)
    console.log(result1.data);
    if(result1.data.code == 200){
        dynamicCategory.value = result1.data.data
    }
}
const changePage = ()=>{

}
const getAllGoods = async()=>{
    
}
</script>

<style scoped lang="scss">
.main {
    .el-card {
        margin-left: 20px;
        margin-right: 30px;

        span {
            margin-left: 30px;
        }

        .el-input {
            width: 250px;
        }

        .category {
            .btn1 {
                font-size: 16px;
                width: 100px;
                height: 50px;
                margin-left: 30px;
                border-top: none;
                border-left: none;
                border-right: none;
                background-color: white;
            }

            .btn2 {
                font-size: 16px;
                width: 100px;
                height: 50px;
                margin-left: 30px;
                border-top: none;
                border-left: none;
                border-right: none;
                background-color: white;
            }


        }

        .list {
            .tableList {
                .el-table {
                    margin-top: 10px;
                    margin-left: 30px;
                    margin-right: 30px;
                }

            }

            .pagination {
                margin-top: 20px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
        }
    }
}
</style>