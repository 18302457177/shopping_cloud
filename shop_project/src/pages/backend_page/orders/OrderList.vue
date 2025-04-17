<template>
    <div class="main">
        <el-card>
            <div class="search">
                <el-input v-model="orderSn" placeholder="请输入内容"></el-input>
                <el-button @click="searchOrderSn" icon="Search"></el-button>
            </div>
            <div class="list">
                <el-table  border>
                    <el-table-column type="index" width="50" />
                    <el-table-column prop="orderSn" label="订单编号" />
                    <el-table-column prop="orderMoney" label="订单价格" />
                    <el-table-column prop="操作" label="是否付款" />
                    <el-table-column prop="操作" label="是否发货" />
                    <el-table-column prop="createTime" label="下单时间" />
                    <el-table-column prop="操作" label="操作">

                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize" :page-sizes="[20, 35, 40, 50]"
                    layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="getOrderPage"
                    @current-change="getOrderPage" />
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="OrderList">
import { reqGetStoreOrder, reqQueryOrder } from '@/api/orderApi';
import { useUserStore } from '@/store/user/user';
import { onMounted, ref,watch } from 'vue';
import VoiceAnnouncementsInstance from '@/util/VoiceAnnouncements';
let userStore = useUserStore();
let orderList = ref([])
let pageNo = ref(1);
let pageSize = ref(20)
let total = ref()
let orderSn = ref()
onMounted(async () => {
    getOrderPage()
})
const getOrderPage = async () => {
    let result = await reqGetStoreOrder(userStore.storeId, pageNo.value, pageSize.value)
    if (result.data.code == 200) {
        orderList.value = result.data.data.data
        total.value = result.data.data.total
    }
}
const searchOrderSn = async () => {
    if (orderSn != null) {
        let result = await reqQueryOrder(orderSn.value)
        if(result.data.code==200){
            orderList.value = result.data.data
        }
    }
}
</script>

<style scoped lang="scss">
.main {
    .el-card {
        .search {
            .el-input {
                width: 250px;
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