<template>
    <div class="main">
        <el-card v-show="addGoodsShow">
            <div class="search">
                <el-input placeholder="请输入内容">
                </el-input>
                <el-button class="btn" icon="Search"></el-button>
                <el-button class="addGoods" type="primary" @click="addGoods">添加商品</el-button>
                <el-button class="addGoods" type="primary" @click="refresh">刷新</el-button>
            </div>
            <div class="list">
                <el-table :data="allSpu" border>
                    <el-table-column type="index" width="50" />
                    <el-table-column prop="name" label="商品名称" />
                    <el-table-column label="商品图片">
                        <template #="{ row }">
                            <img style="width: 150px;height: 150px;" :src="row.img" alt="" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="description" label="商品描述" />
                    <el-table-column prop="姓名" label="折扣" />
                    <el-table-column prop="saleQuantity" label="销量(万台)" />
                    <el-table-column prop="price" label="商品价格(元)" />
                    <el-table-column prop="sales" label="销售额(万元)" />
                    <el-table-column prop="createTime" label="创建时间" />
                    <el-table-column prop="操作" label="操作">
                        <template #="{ row }">
                            <el-button type="primary" icon="Edit"></el-button>
                            <el-button @click="deleteSpu(row.id)" type="danger" icon="Delete"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize"
                    :page-sizes="[3, 5, 10, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="sizeChange" @current-change="pageChange" />
            </div>
        </el-card>
        <el-card v-show="!addGoodsShow">
            <AddGoods></AddGoods>
            <SetGoods ref="setGoddsRef"></SetGoods>
            <AddGoodsParams ref="addGoodsParamRef"></AddGoodsParams>
            <el-button @click="submit" type="primary">提交</el-button>
            <el-button @click="addGoodsShow = true" type="primary">取消</el-button>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="GoodsList">
import { reqDeleteSpu, reqGetAllSpu, reqGoodsPage } from '@/api/goodsApi';
import { onMounted, ref } from 'vue';
import AddGoods from './addGoods/AddGoods.vue';
import SetGoods from './addGoods/SetGoods.vue';
import AddGoodsParams from './addGoods/AddGoodsParams.vue';
import { useAddGoodsStore } from '@/store/goods/addGoods';
import { useUserStore } from '@/store/user/user';
import { ElMessage } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';
let addGoodsStore = useAddGoodsStore()
let userStore = useUserStore()
let $route = useRoute()
let $router = useRouter()
let allSpu = ref([])
let pageNo = ref(1)
let pageSize = ref(3)
let total = ref()
onMounted(async () => {
    getGoodsPage()
})
const getGoodsPage = async () => {
    let result = await reqGoodsPage(userStore.storeId,
        $route.query.pageNo ? $route.query.pageNo : pageNo.value,
        $route.query.pageSize ? $route.query.pageSize : pageSize.value)
    console.log(result.data);
    if (result.data.code == 200) {
        allSpu.value = result.data.data.data
        total.value = result.data.data.total
    }

}
const pageChange = (val:any) => {
    $router.replace({
        path: $route.path,
        query: {
            pageNo: val,
            pageSize: pageSize.value
        }
    })
    getGoodsPage()
}
const sizeChange = ()=>{

}
const refresh = ()=>{
    window.location.reload()
}
let addGoodsShow = ref(true)
const addGoods = () => {
    addGoodsShow.value = !addGoodsShow.value
}
let setGoddsRef = ref()
let addGoodsParamRef = ref()
const submit = () => {
    setGoddsRef.value.saveGoodsToStore()
    addGoodsParamRef.value.saveSkuToStore()
    addGoodsStore.addGoods()
}
const deleteSpu = async (id: any) => {
    let result = await reqDeleteSpu(id)
    if (result.data.code == 200) {
        ElMessage({
            message: '删除成功',
            type: 'success',
            plain: true
        })
    } else {
        ElMessage({
            message: '删除失败',
            type: 'error',
            plain: true
        })
    }
}
</script>

<style scoped lang="scss">
.main {
    .el-card {
        margin-left: 20px;
        margin-right: 30px;

        .search {
            .el-input {
                width: 250px;
            }
        }

        .list {
            .el-table {
                margin-top: 20px;
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
</style>