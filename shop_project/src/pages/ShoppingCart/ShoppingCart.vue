<template>
    <div class="main">
        <Header></Header>
        <Logo></Logo>
        <div class="card">
            <el-table ref="tableRef" height=450px style="width: 80%" :data="cartList" @selection-change="change"
                scrollbar-always-on=true>
                <el-table-column type="selection" width="55" />
                <el-table-column label="序号" width="80px" align="center" type="index" />
                <el-table-column label="图片" width="200">
                    <template #="{ row }">
                        <img :src="row.img" alt="" style="width: 80px;height: 80px;">
                    </template>
                </el-table-column>
                <el-table-column label="商品名称" prop="name" />
                <el-table-column label="单价" prop="onlyMoney" />
                <el-table-column label="小计" prop="sumMoney" />
                <el-table-column label="数量" prop="count">
                    <template #="{ row }">
                        <el-input-number v-model="row.count" :min="1" :max="100"
                            @change="changeCount(row.id, row.count)" />
                    </template>
                </el-table-column>
                <el-table-column label="操作" prop="name" align="center">
                    <template #="{ row }">
                        <el-popconfirm @confirm="deleteCart(row.id)" icon-color="red" icon="Delete" width="200px"
                            title="你确定要删除购物车吗?">
                            <template #reference>
                                <el-button type="danger" icon="Delete" circle />
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="page">
            <div class="pagination">
                <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize" :page-sizes="[5, 10, 15, 20]"
                    :background="true" layout="prev, pager, next, jumper,->,sizes,total" :total="total"
                    @size-change="changePage" @current-change="changePage" />
                <span class="total">总计：</span>
                <span class="account">￥{{ totalMoney }}</span>
                <el-button type="success" style="margin-left: 100px;" @click="settlement">结算</el-button>
                <el-button type="danger" style="margin-left: 100px;" @click="deleteBatch">批量删除</el-button>
            </div>

        </div>
        <el-dialog v-model="dialogVisible" :title="modifyAddrCard ? '修改地址' : '确认购物车'"
            :width="modifyAddrCard ? 500 : 900">
            <el-table v-show="!modifyAddrCard" :data="selected" scrollbar-always-on=true height="400px">
                <el-table-column label="序号" width="80px" align="center" type="index" />
                <el-table-column label="图片" width="200">
                    <template #="{ row }">
                        <img :src="row.img" alt="" style="width: 80px;height: 80px;">
                    </template>
                </el-table-column>
                <el-table-column label="商品名称" prop="name" />
                <el-table-column label="数量" prop="count">
                </el-table-column>
                <el-table-column label="小计" prop="sumMoney" />
            </el-table>
            <el-card v-for="address in userStore.userInfo.addresses" v-show="modifyAddrCard"
                style="margin-bottom: 10px;">
                <div class="userInfo">
                    <span>{{ address.name }}</span> <span>{{ address.phone }}</span>
                </div>
                <div class="addrInfo">
                    <span>{{ address.address }}</span>
                </div>
                <template #footer>
                    <input @click="changeAddr(address.id)" type="radio" name="addr"
                        :checked="address.selected == 1 ? true : false">
                    <span v-if="address.selected == 1" style="color: red;">已默认</span>
                    <span v-else>默认</span>
                    </input>
                </template>
            </el-card>
            <el-card style="margin-bottom: 10px;" v-show="addAddrCard">
                <div class="userInfo">
                    <el-input v-model="addAddrForm.name" placeholder="请输入姓名"/><el-input v-model="addAddrForm.phone" placeholder="请输入手机号码"/>
                </div>
                <div class="addrInfo">
                    <el-input v-model="addAddrForm.address" placeholder="请输入地址"/>
                </div>
            </el-card>
            <template #footer>
                <div class="dialog-footer">
                    <div class="addr" v-show="!modifyAddrCard" @click="modifyAddrCard = !modifyAddrCard">
                        收获地址：{{ userStore.userInfo.defaultAddr.address }}
                    </div>
                    <div class="right">
                        <span v-show="!modifyAddrCard" style="color: red;margin-right: 20px;font-size: large;">总计 ￥{{
                            totalMoney }}</span>
                        <el-button v-show="!modifyAddrCard" @click="dialogVisible = false">取消</el-button>
                        <el-button v-show="!modifyAddrCard" type="primary" @click="submit">
                            结算
                        </el-button>
                        <el-button @click="addAddr" type="danger" icon="CirclePlus"
                            v-show="modifyAddrCard">添加收货地址</el-button>
                        <el-button @click="cancel" v-show="modifyAddrCard">取消</el-button>
                        <el-button @click="editAddr" type="primary" v-show="modifyAddrCard">确定</el-button>
                    </div>
                </div>

            </template>
        </el-dialog>
    </div>

</template>

<script setup lang="ts" name="ShoppingCart">
import Header from '@/components/Header.vue';
import Logo from '@/components/Logo.vue';
import { reqDeleteCart, reqGetOwnCart, reqSubmitCart, reqUpdateCartCount,reqDeleteBatchCart } from '@/api/cartApi';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/store/user/user';
import { ElMessage } from 'element-plus';
import { reqChangeDefaultAddr } from '@/api/useApi';
let userStore = useUserStore()
let cartList = ref([])
let pageNo = ref<number>(1)
let pageSize = ref<number>(5)
let totalMoney = ref(0)
let total = ref()
let dialogVisible = ref(false)
let addAddrCard = ref(false)
let addAddrForm = ref({
    userId:userStore.userId,
    name:'',
    phone:'',
    address:''
})
onMounted(() => {
    getPage()
})
const changePage = () => {
    getPage()
}

const getPage = async () => {
    let result = await reqGetOwnCart(userStore.userId, pageNo.value, pageSize.value)
    if (result.data.code == 200) {
        cartList.value = result.data.data.data
        total.value = result.data.data.total
    }

}
const deleteBatch = async()=>{
    let cartIds: any[] = []
    selected.value.forEach(element => {
        cartIds.push(element.id)
    });
    if(selected.value.length!=0){
        let result = await reqDeleteBatchCart(cartIds)
        if(result.data.code == 200){
            ElMessage({
                message:'删除成功',
                type:'success',
                plain:true
            })
            setTimeout(() => {
                window.location.reload()
            }, 1000);
        }else{
            ElMessage({
                message:'删除失败',
                type:"error",
                plain:true
            })
        }
    }
}
const deleteCart = async (id: string) => {
    let result = await reqDeleteCart(userStore.userId, id)
    if (result.data.code == 200) {
        window.location.reload()
        ElMessage({
            type: 'success',
            message: '删除成功',
            plain: true
        })
    } else {
        ElMessage({
            type: 'error',
            message: '删除失败',
            plain: true
        })
    }

}
const changeCount = async (id: number, count: number) => {

    let result = await reqUpdateCartCount(id, count)
    if (result.data.code == 200) {
        window.location.reload()
    }
}
let selected = ref([])
const tableRef = ref()
const change = () => {
    totalMoney.value = 0
    selected.value = JSON.parse(JSON.stringify(tableRef.value.getSelectionRows()))
    selected.value.forEach(element => {
        totalMoney.value += element.sumMoney
    });
}
const settlement = () => {
    dialogVisible.value = true
}

const submit = async () => {
    let submitData = ref({
        id: userStore.userId,
        totalMoney: totalMoney.value,
        cartLists: JSON.parse(JSON.stringify(selected.value))
    })
    if (submitData.value.totalMoney != 0) {
        let result = await reqSubmitCart(submitData.value)
        if (result.data.code == 200) {
            ElMessage({
                type: 'success',
                message: '购买成功',
                plain: true
            })
            dialogVisible.value = false
        } else {
            ElMessage({
                type: 'error',
                message: '购买失败',
                plain: true
            })
        }
    }
}
let modifyAddrCard = ref(false)
let addressId = ref()
const editAddr = async() => {
    modifyAddrCard.value = !modifyAddrCard.value
    addAddrCard.value = false
    await reqChangeDefaultAddr(userStore.userId, addressId.value)
}
const cancel = () => {
    modifyAddrCard.value = !modifyAddrCard.value
    addAddrCard.value = false
}
const addAddr = () => {
    addAddrCard.value = true
}

const changeAddr =  (id: number) => {
    addressId.value = id
}
</script>

<style scoped lang="scss">
.main {
    .card {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .page {
        width: 100%;

        .pagination {
            margin-top: 20px;
            width: 90%;
            display: flex;
            justify-content: center;

            .total {
                margin-left: 50px;
                margin-top: 5px;
            }

            .account {
                margin-left: 5px;
                margin-top: 5px;
                color: red;
            }
        }
    }

    .dialog-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .addr {
            font-size: small;
            color: rgb(132, 132, 241);
            cursor: pointer;
        }
    }


    .userInfo {
        margin-bottom: 10px;

        span {
            font-size: 16px;
            font-weight: 700;
        }
    }

    .addrInfo {
        span {
            font-size: 16px;
            font-weight: 700;
        }
    }
}
</style>