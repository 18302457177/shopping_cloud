<template>
    <div class="main">
        <div class="left">
            <div class="logo">
                <SvgIcon name="cloud" width="65" height="65"></SvgIcon>
                <span>Cloud商城店铺后台</span>
            </div>
        </div>
        <div class="right">
            <div class="user">
                <div class="head">
                    <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" alt="">
                </div>
                <div class="back" @click="goHome">返回前台</div>
                <div class="name">
                    xxs
                </div>
                <div class="logout" @click="logout">
                    退出
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="BackHeader">
import SvgIcon from '@/components/SvgIcon/SvgIcon.vue';
import { useUserStore } from '@/store/user/user';
import { useRouter } from 'vue-router';
let $router = useRouter()
let userStore = useUserStore()
const goHome = ()=>{
    $router.push('/home')
}
const logout = () => {
    userStore.logout().then(() => {
        setTimeout(() => {
            $router.push('/login')
        }, 500)
    })
}
</script>

<style scoped lang="scss">
.main {
    width: 100%;
    height: 100%;
    background-color: #313b45;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .left {
        .logo {
            margin-left: 20px;
            display: flex;
            color: white;
            font-size: 40px;
            align-items: center;

            img {
                height: 50px;
                width: 50px;
            }
        }
    }

    .right {
        .user {
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;

            .head {
                cursor: pointer;

                img {
                    height: 45px;
                    width: 45px;
                    border-radius: 50%;
                }

                margin: 0 10px;
            }
            .back{
                cursor: pointer;
                margin-right: 10px;
            }

            .name {
                cursor: pointer;
                margin-right: 10px;
            }

            .logout {
                margin-right: 10px;
                cursor: pointer;
            }
        }
    }
}
</style>