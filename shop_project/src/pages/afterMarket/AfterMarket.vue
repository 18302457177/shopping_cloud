<template>
    <Header></Header>
    <Logo></Logo>
    <div class="container">
        <div class="main">
            <el-card shadow="hover">
                <template #header>
                    <div class="card-header">
                        <span>选择售后类型</span>
                    </div>
                </template>
                <div class="order-description">
                    <div class="img">
                        <img :src="order.img" alt="">
                    </div>
                    <div class="description">
                        <div class="description-top">{{ order.name }}</div>
                        <div class="description-bottom"></div>
                        <div class="delivery-status">
                            <img :src="order.delivery.img" alt="">
                            <span class="status">{{ order.goodsStatus }}</span>
                            <span class="status-description">您的快件已签收，签收人。。。。</span>
                        </div>
                    </div>

                </div>
                <template #footer>
                    <div class="service">
                        <span style="color: black;margin: 0 10px 0 30px;">可享3项服务</span>
                        <span style="color: gray;font-size: 14px;">运费险(预计赔付<em
                                style="color: red;">10.5元</em>)、7天无理由退货、急速退款</span>
                    </div>
                </template>
            </el-card>
            <el-card style="margin-top: 20px;" shadow="hover">
                <template #header>
                    <div class="refund-type1" @click="showDialog(1)">
                        <div class="refund-type1-left">
                            <SvgIcon name="TuiKuan" color="red" width="25px" height="25px"></SvgIcon>
                        </div>
                        <div class="refund-type1-right">
                            <div class="refund-type1-right-top">
                                <span>仅退款（无需退货）</span>
                            </div>
                            <div class="refund-type1-right-bottom">
                                <span>未收到货/已拒绝,或与商家协商一致不用退货只退款</span>
                            </div>
                        </div>
                    </div>
                </template>
                <div class="refund-type2" @click="showDialog(2)">
                    <div class="refund-type2-left">
                        <SvgIcon name="TuiHuoTuiKuan" color="red" width="25px" height="25px"></SvgIcon>
                    </div>
                    <div class="refund-type2-right">
                        <div class="refund-type2-right-top">
                            <span>退货退款</span>
                        </div>
                        <div class="refund-type2-right-bottom">
                            <span>已收到货,需退还收到的货物</span>
                        </div>
                    </div>
                </div>
                <template #footer>
                    <div class="refund-type3" @click="showDialog(3)">
                        <div class="refund-type3-left">
                            <SvgIcon name="HuanHuo" color="red" width="25px" height="25px"></SvgIcon>
                        </div>
                        <div class="refund-type3-right">
                            <div class="refund-type3-right-top">
                                <span>换货</span>
                            </div>
                            <div class="refund-type3-right-bottom">
                                <span>已收到货,需更换收到的货物</span>
                            </div>
                        </div>
                    </div>
                </template>
            </el-card>
            <el-dialog v-model="dialogVisible" :title="title" width="500">
                <el-form :model="formData" label-width="120px">
                    <el-form-item label="收货状态">
                        <el-select v-model="applyData.receiptStatus" placeholder="点击选择收获状态" style="width: 250px">
                            <el-option v-for="item in marketStatus" :key="item.value" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="申请原因">
                        <el-select v-model="applyData.applyReason" placeholder="点击选择申请原因" style="width: 250px">
                            <el-option v-for="item in applyReasons" :key="item.value" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="申请金额">
                        <SvgIcon name="Renminbi" color="red" width="20px" height="20px"></SvgIcon>
                        <span>{{ order.orderAccount }}</span>
                    </el-form-item>
                    <el-form-item label="申请说明">
                        <el-input v-model="applyData.applyInstruction" type="textarea" style="width: 250px;" placeholder="必填,请您详细填写申请说明"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话">
                        <el-input style="width: 250px;" v-model="applyData.phone"></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="danger" @click="submitApply">
                            提交申请
                        </el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script setup lang="ts" name="AfterMarket">
import { reqSearchOrder,reqApply } from '@/api/orderApi';
import Header from '@/components/Header.vue';
import Logo from '@/components/Logo.vue';
import SvgIcon from '@/components/SvgIcon/SvgIcon.vue';
import { useOrderStore } from '@/store/order';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
let orderStore = useOrderStore()
let order = ref({
    delivery: {}
})
let afterMarketCategory = ref([
    {
        id: 1,
        category: '退款'
    },
    {
        id: 2,
        category: '退货退款'
    },
    {
        id: 3,
        category: '换货'
    }
])
const marketStatus = [
  {
    value: '未收货',
    label: '未收货',
  },
  {
    value: '已收货',
    label: '已收货',
  },
]
const applyReasons = [
    {
        value:'材质、面料与商品描述不符',
        label:'材质、面料与商品描述不符'
    },
    {
        value:'大小尺寸与商品描述不符',
        label:'大小尺寸与商品描述不符'
    },
    {
        value:'颜色、款式、图案与描述不符',
        label:'颜色、款式、图案与描述不符'
    },
    {
        value:'质量问题',
        label:'质量问题'
    },
    {
        value:'收到商品少件(含少配件)',
        label:'收到商品少件(含少配件)'
    },
    {
        value:'商品破损或污损',
        label:'商品破损或污损'
    },
    {
        value:'空包裹',
        label:'空包裹'
    },
    {
        value:'商家发错货',
        label:'商家发错货'
    },
    {
        value:'假冒品牌',
        label:'假冒品牌'
    },
    {
        value:'其它原因',
        label:'其它原因'
    }
]
let applyData = ref({
    receiptStatus:'',
    applyReason:'',
    orderId:orderStore.orderId,
    applyInstruction:'',
    phone:''
})
const submitApply = async()=>{
    dialogVisible.value = false;
    let result = await reqApply(applyData.value)
    if(result.data.code == 200){
        ElMessage({
            message:'申请成功',
            type:'success',
            plain:true
        })
    }
}
let formData =
    onMounted(async () => {
        let result = await reqSearchOrder(orderStore.orderId)
        console.log(result.data);
        if (result.data.code == 200) {
            order.value = result.data.data
        }
    })
let $router = useRouter()
let dialogVisible = ref(false)
let title = ref()
const showDialog = (id: number) => {
    dialogVisible.value = true
    for (let i = 0; i < afterMarketCategory.value.length; i++) {
        if (afterMarketCategory.value[i].id == id) {
            title.value = afterMarketCategory.value[i].category
        }
    }
}
</script>

<style scoped lang="scss">
.container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;

    .main {
        width: 55%;
        height: 300px;

        .card-header {
            span {
                font-size: 20px;
                letter-spacing: 2px;
                color: black;
                font-weight: 550;
            }
        }

        .order-description {
            display: flex;
            align-items: center;

            .img {
                img {
                    border-radius: 10px;
                    margin-left: 30px;
                    width: 150px;
                    height: 150px;
                    margin-right: 20px;
                }
            }

            .description {
                .description-top {
                    font-size: 20px;
                    letter-spacing: 3px;
                    font-weight: 600;
                    margin-bottom: 15px;
                }

                .description-bottom {
                    font-size: 14px;
                    color: gray;
                    margin-bottom: 20px;
                }

                .delivery-status {
                    display: flex;
                    align-items: center;

                    img {
                        border-radius: 50%;
                        width: 30px;
                        height: 30px;
                    }

                    .status {
                        margin: 0 10px;
                    }

                    .status-description {
                        font-size: 14px;
                        color: gray;
                    }
                }
            }
        }

        .refund-type1 {
            display: flex;
            align-items: center;
            margin-left: 20px;
            cursor: pointer;

            .refund-type1-left {
                margin-right: 15px;
            }

            .refund-type1-right {
                .refund-type1-right-top {
                    margin-bottom: 10px;

                    span {
                        font-size: 16px;
                        color: black;
                        font-weight: 700;
                    }
                }

                .refund-type1-right-bottom {
                    span {
                        color: gray;
                        font-size: 14px;
                    }
                }
            }
        }

        .refund-type2 {
            display: flex;
            align-items: center;
            margin-left: 20px;
            cursor: pointer;

            .refund-type2-left {
                margin-right: 15px;
            }

            .refund-type2-right {
                .refund-type2-right-top {
                    margin-bottom: 10px;

                    span {
                        font-size: 16px;
                        color: black;
                        font-weight: 700;
                    }
                }

                .refund-type2-right-bottom {
                    span {
                        color: gray;
                        font-size: 14px;
                    }
                }
            }
        }

        .refund-type3 {
            display: flex;
            align-items: center;
            margin-left: 20px;
            cursor: pointer;

            .refund-type3-left {
                margin-right: 15px;
            }

            .refund-type3-right {
                .refund-type3-right-top {
                    margin-bottom: 10px;

                    span {
                        font-size: 16px;
                        color: black;
                        font-weight: 700;
                    }
                }

                .refund-type3-right-bottom {
                    span {
                        color: gray;
                        font-size: 14px;
                    }
                }
            }
        }
    }
}
</style>