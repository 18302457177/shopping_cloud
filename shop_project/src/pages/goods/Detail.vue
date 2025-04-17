<template>
    <Header />
    <Logo />
    <div class="main">
        <div class="content">
            <el-image style="width: 100%; height: 100%" :src="goodDetail.img" :zoom-rate="1.2" :max-scale="7"
                :min-scale="0.2" :preview-src-list="Imgs" :initial-index="4" fit="cover" />
        </div>
        <div class="detail">
            <div class="name">
                <span>{{ goodDetail.name }}</span>
            </div>
            <div class="money">
                <span>￥{{ goodDetail.price }}</span>
            </div>
            <div class="description">
                <span>{{ goodDetail.description }}</span>
            </div>
            <div class="categories">
                <div class="bottom" v-for="(good,index) in goodDetail.skuDtoList">
                    <span>{{ good.param }}</span>
                    <el-card shadow="hover"  :key="index" v-for="(param,index) in good.children"
                        style="cursor: pointer;width: 300px;height: 40px;"
                        :body-style="{ display: 'flex', padding: '0px !important' }"
                        :class="param.choose === index ? 'active' : ''" @click.native="changeColer(index,param)">
                        <div class="categories-detail" @click="categoryId = good.id">
                            <div class="left">
                                <!-- <img :src="good.img" alt="" style="width: 40px;height: 40px;"> -->
                            </div>
                            <div class="right">
                                <span >{{ param.param }}</span>
                                <div class="right-right">
                                    <SvgIcon name="Renminbi" width="20px" height="20px"></SvgIcon>
                                    <span>{{ goodDetail.price }}</span>
                                </div>
                            </div>
                        </div>
                    </el-card>

                    
                </div>
                <el-button type="danger" size="large" @click="addCart">加入购物车</el-button>
            </div>

        </div>

    </div>
    <div class="remark">
        <div class="center">
            <el-card style="margin-bottom: 10px;" v-for="i in 4">
                <template #header>
                    <div class="card-header">
                        <!-- <img src="@\sources\carousel\th.jpg" alt="头像"
                            style="border-radius: 50%;width: 40px;height: 40px;margin-right: 20px;"> -->
                        <span>用户名</span>
                    </div>
                </template>
                <span>哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈红红火火恍恍惚惚</span>
                <template #footer>
                    <div class="footer">
                        <div class="reply">
                            <el-icon>
                                <ChatRound />
                            </el-icon>
                            <span>回复</span>
                        </div>
                        <div class="share">
                            <el-icon>
                                <Share />
                            </el-icon>
                            <span>分享</span>
                        </div>
                        <div class="like">
                            <el-icon>
                                <StarFilled />
                            </el-icon>
                        </div>
                    </div>
                </template>
            </el-card>
        </div>
    </div>


</template>

<script setup lang="ts" name="Detail">
import { ref } from 'vue';
import { reqGetGoodsDetail } from '@/api/goodsApi';
import Header from '@/components/Header.vue';
import Logo from '@/components/Logo.vue';
import { useGoodsStore } from '@/store/goods/goods';
import { onMounted } from 'vue';
import { useUserStore } from '@/store/user/user';
import { reqAddCart } from '@/api/cartApi';
import { ElMessage } from 'element-plus';
let userStore = useUserStore()
let goodsStore = useGoodsStore()
let goodDetail = ref({})
let Imgs = ref([])
onMounted(async () => {
    let result1 = await reqGetGoodsDetail(goodsStore.goodsId)
    if (result1.data.code == 200) {
        goodDetail.value = result1.data.data
        // goodDetail.value.goodsImgs.forEach(element => {
        //     Imgs.value.push(element.img)
        // });
    }
})
const changeColer = (index: number,param:any) => {
    console.log(param);
    cartData.value.skuParamId.push(param.skuId)
    if (param.choose != index) {
        param.choose = index
    } else {
        param.choose = -1
    }
    addCartDto.value.skuparamId.push(param.skuId)

}
let cartData = ref({
    spuId:goodsStore.goodsId,
    skuParamId:[] as any
})
let addCartDto = ref({
    id:0,
    userId:userStore.userId,
    spuId:goodsStore.goodsId,
    skuparamId:[] as any,
    count:'1',
    selected:'0',
    timestamp:'',
    img:'',
    
})
const addCart = async () => {

    let result = await reqAddCart(addCartDto.value)
    if (result.data.code == 200) {
        ElMessage({
            type: 'success',
            message: "加入购物车成功",
            plain: true
        })
    } else {
        ElMessage({
            type: 'error',
            message: "加入购物车失败",
            plain: true
        })
    }
}

</script>

<style scoped lang="scss">
.main {
    width: 100%;
    display: flex;
    height: auto;

    .content {
        width: 450px;
        height: 450px;
        background-color: honeydew;
        position: relative;
        left: 20%;
        top: 3%;

        img {
            width: 100%;
            height: 100%;
        }
    }

    .detail {
        width: 650px;
        height: 600px;
        // background-color: rgb(243, 240, 241);
        position: relative;
        left: 20%;
        top: 3%;
        padding: 20px;

        .name {
            span {
                color: rgb(84, 67, 67);
                font-size: 3em;
                font-weight: 700;

            }
        }

        .money {
            margin-top: 20px;

            span {
                color: red;
                font-size: 2em;
                font-weight: 700;
            }
        }

        .description {
            margin-top: 20px;

            span {
                font-size: 1.5em;
            }
        }

        .categories {
            width: 100%;
            height: 200px;
            margin-top: 10px;

            .bottom {
                .el-card.active {
                    color: red;
                    border: 1.5px solid red;
                }

                .categories-detail {
                    display: flex;
                    align-items: center;
                    width: 100%;

                    .right {
                        width: 100%;
                        display: flex;
                        align-items: center;
                        justify-content: space-between;

                        .right-right {
                            display: flex;
                            align-items: center;
                            margin-right: 10px;
                        }
                    }
                }

                .el-button {
                    margin-top: 20px;
                    font-size: 15px;
                }
            }
        }


    }

}

.remark {
    width: 100%;
    display: flex;
    justify-content: center;

    .center {
        width: 53%;
        background-color: rgb(84, 67, 67);
        padding: 70px;

        .card-header {
            display: flex;
            align-items: center;
        }

        .footer {
            display: flex;
            align-items: center;

            .reply {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 15px;
            }

            .share {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 15px;
            }

            .like {
                display: flex;
                align-items: center;
                justify-content: center;
            }
        }
    }
}
</style>