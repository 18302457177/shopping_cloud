<template>
    <div class="top">
        <div class="header">
            <div class="left">
                <el-icon>
                    <Location />
                </el-icon>
                <span>黑龙江</span>
            </div>
            <div class="right">
                <div class="block">
                    <el-avatar @click="goUser" :size="30" :src="userStore.avatar"/>
                </div>
                <span @click="gohome">首页</span>
                <span @click="goOrder">我的订单</span>
                <span @click="goShoppingCart">购物车</span>
                <span @click="goUser">个人信息</span>
                <span @click="goBackground">店铺后台</span>
                <span @click="logout">退出登录</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Header">
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user/user';
import { ref } from 'vue';
let userStore = useUserStore()
let $router = useRouter()

const gohome = () => {
    $router.push('/home')
}
const goOrder = () => {
    $router.push('/order')
}
const goShoppingCart = () => {
    $router.push('/ShoppingCart')
}
const logout = () => {
    userStore.logout().then(() => {
        $router.push('/login')
    })
}
const goAddGoods = () => {
    $router.push('/addGoods')
}
const goBackground = () => {
    $router.push('/background')
}
const goUser = () => {
    $router.push('/user')
}
</script>

<style scoped lang="scss">
.top {
    width: 100%;
    height: 5vh;
    display: flex;
    justify-content: center;

    .header {
        width: 80%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .left {
            display: flex;
            align-items: center;
            padding-left: 10px;
            color: grey;
            cursor: pointer;

            &:hover {
                color: red;

                span {
                    margin-left: 3px;
                }
            }
        }

        .right {
            font-size: 12px;
            color: grey;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;

            span {
                margin-right: 10px;

                &:hover {
                    color: red;
                }
            }
        }
    }
}
</style>