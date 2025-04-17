<template>
    <Header></Header>
    <Logo></Logo>
    <div class="content">
        <div class="detail">
            <el-card class="card-top">
                <div class="search">
                    <el-input placeholder="搜索订单" v-model="OrderForm"></el-input>
                    <el-button type="primary" @click="searchOrder">搜索</el-button>
                </div>

            </el-card>

                <el-table style="width: 100%;margin-top: 5px;" :data="orderList" height=400px scrollbar-always-on=true>
                    <el-table-column type="selection" width="55" />
                    <el-table-column type="index" label="序号" width="80" />
                    <el-table-column prop="orderId" label="订单号" width="150" />
                    <el-table-column label="图片" width="150">
                    <template #="{ row }">
                        <img :src="row.img" alt="" style="width: 80px;height: 80px;">
                    </template>
                </el-table-column>
                    <el-table-column prop="orderAccount" label="交易金额" width="100" />
                    <el-table-column prop="status" label="状态" width="100">
                        <template #="{ row }">
                            <span v-if="row.status == 0" style="color: red;">先用后付</span>
                            <span v-else="row.status == 1" style="color: green;">已支付</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="400" align="center">
                        <template #="{ row }">
                            <el-button @click="goAfterMarket(row.orderId)">申请售后</el-button>
                            <el-button>再买一单</el-button>
                            <el-popconfirm @confirm="deleteOrder(row.id)" icon-color="red" icon="Delete" width="200px"
                                title="你确定要删除该订单吗?">
                                <template #reference>
                                    <el-button type="danger" icon="Delete" circle />
                                </template>
                            </el-popconfirm>
                            <el-dropdown style="margin-left: 20px;margin-top: 7px;">
                                <span class="el-dropdown-link">
                                    更多
                                    <el-icon class="el-icon--right">
                                        <arrow-down />
                                    </el-icon>
                                </span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item>查看物流</el-dropdown-item>
                                        <el-dropdown-item>追加评价</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </template>
                    </el-table-column>
                </el-table>

            <div class="page">
                <div class="pagination">
                    <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize"
                        :page-sizes="[5, 10, 15, 20]" :background="true"
                        layout="prev, pager, next, jumper,->,sizes,total" :total="total" @size-change="changePage"
                        @current-change="changePage" />
                </div>
            </div>
        </div>
    </div>
    <el-dialog v-model="dialogVisible" title="搜索结果" width="800">
        <div class="table">
            <div class="order">
                {{ order.orderId }}
            </div>

            <div class="money">
                {{ order.orderAccount }}
            </div>
            <div class="status">
                <span v-if="order.status == 0" style="color: #777;">未支付</span>
                <span v-else="order.status == 1" style="color: green;">已支付</span>
            </div>
            <div class="operate">
                <el-button>申请退款</el-button>
                <el-button>再买一单</el-button>
                <el-popconfirm @confirm="deleteOrder(row.id)" icon-color="red" icon="Delete" width="200px"
                    title="你确定要删除该订单吗?">
                    <template #reference>
                        <el-button type="danger" icon="Delete" circle />
                    </template>
                </el-popconfirm>
                <el-dropdown style="margin-left: 20px;margin-top: 7px;">
                    <span class="el-dropdown-link">
                        更多
                        <el-icon class="el-icon--right">
                            <arrow-down />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>查看物流</el-dropdown-item>
                            <el-dropdown-item>追加评价</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">
                    关闭
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup lang="ts" name="Order">
import { reqDeleteOrder, reqOrder, reqOrderPage, reqSearchOrder } from '@/api/orderApi';
import Header from '@/components/Header.vue';
import Logo from '@/components/Logo.vue';
import { useOrderStore } from '@/store/order';
import { useUserStore } from '@/store/user';
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
let orderStore = useOrderStore()
let $router = useRouter()
let userStore = useUserStore()
let orderList = ref([])
let pageNo = ref(1)
let pageSize = ref(5)
let total = ref(40)
let OrderForm = ref('')
let order = ref([])
let dialogVisible = ref(false)
onMounted(() => {
    getOrder()
})

const deleteOrder = async (id: string) => {
    let result = await reqDeleteOrder(userStore.userId, id)
    if (result.data.code == 200) {
        ElMessage({
            type:'success',
            message:'删除成功',
            plain:true
        })
    }else{
        ElMessage({
            type:'error',
            message:'删除失败',
            plain:true
        })
    }
}
const changePage = async () => {
    getOrder()
}
const getOrder = async () => {
    let result = await reqOrderPage(userStore.userId, pageNo.value, pageSize.value)
    // console.log(result.data);
    if (result.data.code == 200) {
        total.value = result.data.data.total
        orderList.value = result.data.data.data

    }
}
const searchOrder = async () => {
    let result = await reqSearchOrder(OrderForm.value)
    if (result.data.code == 200) {
        order.value = result.data.data
        dialogVisible.value = true
    }
}
const goAfterMarket = (id:string)=>{
    orderStore.orderId = id
    $router.push('/afterMarket')
}
</script>

<style scoped lang="scss">
.content {
    width: 100%;
    display: flex;
    justify-content: center;
    display: flex;
    justify-content: center;

    .detail {
        width: 70%;
        height: 200px;

        .card-top {
            width: 100%;

            .search {
                .el-input {
                    width: 200px;
                    margin-right: 10px;
                }

                display: flex;

                .el-button {
                    width: 80px;
                }
            }
        }
    }

    .page {
        width: 100%;

        .pagination {
            margin-top: 20px;
            width: 90%;
            display: flex;
            justify-content: center;
        }
    }

}
.table{
    display: flex;
    justify-content: center;
    align-items: center;
    .order{
        margin-right: 20px;
    }
    .money{
        margin-right: 20px;
    }
    .status{
        margin-right: 20px;
    }
}
</style>