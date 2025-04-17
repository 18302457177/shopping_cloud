<template>
    <Header></Header>
    <div class="search">
        <div class="searchContent">
            <div class="left">
                <img src="@\sources\pictures\6847.gif_wh860.gif" alt="">
            </div>
            <div class="searchInput">
                <div class="top">
                    <div class="box">
                        <el-input placeholder=""></el-input>
                        <el-button type="primary" icon="Search">搜索</el-button>
                    </div>
                </div>
                <div class="bottom">
                    <span>小说</span>
                    <span>银杏叶</span>
                    <span>湿厕纸</span>
                    <span>毛毛鞋</span>
                    <span>毛毛鞋</span>
                    <span>毛毛鞋</span>
                    <span>毛毛鞋</span>
                </div>
            </div>
            <div class="right">
                <img src="@\sources\pictures\O1CN012WokpQ1MJCfcG1Usu_!!6000000001413-1-tps-1920-576.gif" alt="">
            </div>
        </div>
    </div>
    <div class="category">
        <div class="middle">
            <div class="left">
                <div class="left-top">
                    <span>分类</span>
                    <img src="@\sources\pictures\O1CN01OPMo5d1SnKKX8RfYM_!!6000000002291-1-tps-72-40.gif" alt="">
                </div>
                <div class="category1">
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                    <div class="type">
                        <SvgIcon class="svg" name="DianNao" width="25px" height="25px"></SvgIcon>
                        <span>电脑</span>
                        <span>/</span>
                        <span>配件</span>
                        <span>/</span>
                        <span>办公</span>
                        <span>/</span>
                        <span>文具</span>
                    </div>
                </div>
            </div>
            <div class="content">
                <div class="top">
                    <div class="top-left">
                        <el-carousel height="150px">
                            <el-carousel-item v-for="item in 4" :key="item">
                                <h3 class="small justify-center" text="2xl">{{ item }}</h3>
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                    <div class="top-right">

                    </div>
                </div>
                <div class="bottom">
                    <div class="bottom-left">

                    </div>
                    <div class="bottom-right">

                    </div>
                </div>
            </div>
            <div class="right">

            </div>
        </div>
    </div>
    <div class="bottom">
        <div class="commodity">
            <el-card style="width:24%; cursor: pointer;" v-for="goods in goodsList" :key="goods.id" shadow="hover">
                <div @click="goDetail(goods.id)">
                    <img :src="goods.img" style="width: 250px;height: 250px;" />
                    <div class="name">
                        {{ goods.name }}
                    </div>
                    <div class="detail">
                        {{ goods.description }}
                    </div>
                    <div class="money">
                        <span>￥{{ goods.price }}</span>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script setup lang="ts" name="Home">
import { reqGetGoods, reqSearchGoods } from '@/api/goodsApi';
import Header from '@/components/Header.vue';
import { useGoodsStore } from '@/store/goods/goods';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import SvgIcon from '@/components/SvgIcon/SvgIcon.vue';
let goodsStore = useGoodsStore()
let $router = useRouter()
let goodsList = ref([])
onMounted(async () => {
    let result = await reqGetGoods()
    // console.log(result.data);
    if (result.data.code == 200) {
        goodsList.value = result.data.data
    }
})
const goDetail = (id: string) => {
    goodsStore.goodsId = id
    $router.push('/detail')
}

</script>
<style scoped lang="scss">
.search {
    height: 12vh;
    // background-color: gray;
    display: flex;
    justify-content: center;

    .searchContent {
        width: 85%;
        // background-color: green;
        height: 12vh;
        display: flex;

        .left {
            img {
                width: 20vh;
                height: 12vh;
            }
        }

        .searchInput {
            height: 12vh;
            width: 80%;
            // background-color: blue;

            .top {
                height: 8vh;
                // background-color: burlywood;
                display: flex;
                justify-content: center;
                align-items: center;

                .box {
                    width: 70%;
                    height: 6vh;
                    display: flex;
                    align-items: center;

                    .el-input {
                        height: 5vh;
                        margin-right: 10px;
                    }
                }
            }

            .bottom {

                // background-color: brown;
                span {
                    margin-right: 15px;
                    color: rgb(108, 99, 99);
                }
            }
        }

        .right {
            img {
                height: 13vh;
            }
        }
    }
}

.category {
    height: 60vh;
    // background-color: gray;
    display: flex;
    justify-content: center;

    .middle {
        width: 90%;
        height: 100%;
        display: flex;

        // background-color: green;
        .left {
            width: 22%;
            height: 92%;
            background-color: rgb(248, 243, 243);
            border-radius: 10px;
            margin-top: 2vh;

            .left-top {
                padding-top: 15px;
                padding-left: 15px;
                display: flex;
                align-items: center;

                span {
                    font-weight: 700;
                }

                img {
                    width: 7vh;
                }
            }

            .category1 {
                border-radius: 15px;
                margin-left: 7px;
                width: 95%;

                .type {
                    margin-top: 13px;
                    display: flex;
                    align-items: center;

                    .svg {
                        margin-left: 15px;
                        margin-right: 10px;
                    }

                    span {
                        cursor: pointer;
                        margin-right: 8px;
                        color: rgb(50, 49, 49);
                        font-size: 15px;
                    }
                }

                :hover {
                    background-color: white;
                }
            }
        }

        .content {
            .top {
                display: flex;
                .top-left {
                    margin-top: 15px;
                    margin-left: 15px;
                    width: 50vh;
                    height: 26vh;
                    background-color: grey;
                    border-radius: 15px;
                }

                .top-right {
                    margin-top: 15px;
                    margin-left: 15px;
                    width: 50vh;
                    height: 26vh;
                    background-color: grey;
                    border-radius: 15px;
                }
            }
            .bottom{
                display: flex;
                .bottom-left {
                    margin-top: 15px;
                    margin-left: 15px;
                    width: 50vh;
                    height: 27vh;
                    background-color: grey;
                    border-radius: 15px;
                }

                .bottom-right {
                    margin-top: 15px;
                    margin-left: 15px;
                    width: 50vh;
                    height: 27vh;
                    background-color: grey;
                    border-radius: 15px;
                }
            }
        }
        .right {
            width: 28%;
            height: 92%;
            background-color: rgb(248, 243, 243);
            border-radius: 10px;
            margin-top: 2vh;

            margin-left: 15px;
            
        }
    }
}

.bottom {
    width: 100%;
    display: flex;
    justify-content: center;

    .commodity {
        width: 80%;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;

        .detail {
            width: 100%;
            height: 50px;
        }

        .money {
            width: 100%;
            color: red;
            font-size: 20px;
        }
    }
}
</style>