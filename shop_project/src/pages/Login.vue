<template>
    <div class="content">
        <div class="top">
            <img src="@\sources\pictures\logo-201305-b.png" alt="">
            <span>京松</span>
        </div>
        <div class="login">
            <el-card v-show="loginType == 0">
                <template #header>
                    <div class="card-header">
                        <span>京松登录</span>
                    </div>
                </template>
                <div v-show="showLogin">
                    <el-form label-width="auto" style="max-width: 600px;margin-left: 20px;">
                        <el-form-item label="用户名:">
                            <el-input v-model="loginForm.username" placeholder="请输入用户名" style="height: 40px;" />
                        </el-form-item>
                        <el-form-item label="密码:">
                            <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"
                                style="height: 40px;margin-top: 10px;" />
                        </el-form-item>
                    </el-form>
                    <div class="logbtn">
                        <el-button @click="login" type="primary"
                            style="width: 48%;height: 40px;margin-top: 15px;">登录</el-button>
                    </div>
                </div>
                <div v-show="!showLogin">
                    <el-form  label-width="auto" style="max-width: 600px;margin-left: 20px;">
                        <el-form-item label="手机号码:">
                            <el-input v-model="phoneForm.phone" placeholder="请输入手机号" style="height: 40px;" />
                        </el-form-item>
                        <el-form-item label="验证码:">
                            <el-input v-model="phoneForm.code" placeholder="请输入验证码"
                                style="height: 40px;width: 145px;margin-top: 10px;" />
                            <el-button @click="getCode"
                                style="height: 40px;margin-top: 10px;margin-left: 5px;">获取验证码</el-button>
                        </el-form-item>
                    </el-form>
                    <div class="logbtn">
                        <el-button @click="loginPhone" type="primary"
                            style="width: 48%;height: 40px;margin-top: 15px;">登录</el-button>
                    </div>
                </div>
                <template #footer>

                    <div class="register">还没有账号?<span @click="goRegister">注册</span></div>
                    <div class="qr">
                        <div class="qr_phone" @click="phoneLogin">
                            <SvgIcon name="HuanHuo" color="red" width="25px" height="25px"></SvgIcon>
                            <span>手机号登录</span>
                        </div>
                        <div class="qr_content" @click="ORorusername">
                            <SvgIcon name="HuanHuo" color="red" width="25px" height="25px"></SvgIcon>
                            <span>微信登录</span>
                        </div>
                    </div>
                </template>
            </el-card>
            <el-card v-show="loginType == 1">
                <template #header>
                    <div class="card-header">
                        <span>微信登录</span>
                    </div>
                </template>
                <div class="QR">
                    <img src="@/sources/pictures/qr.png" alt="">
                </div>
                <template #footer>
                    <div class="usernameLogin">
                        <span @click="ORorusername">用户名登录</span>
                    </div>
                </template>
            </el-card>
        </div>
        <div class="bottom">
            123
        </div>
    </div>
</template>

<script setup lang="ts" name="Login">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user/user';
import SvgIcon from '@/components/SvgIcon/SvgIcon.vue';
import { ElMessage } from 'element-plus';
import { reqPhoneLogin, reqSendCode } from '@/api/useApi';
import { jwtDecode } from 'jwt-decode';
let $router = useRouter()
let userStore = useUserStore()
let loginForm = ref({
    username: '',
    password: ''
})
let phoneForm = ref({
    phone: '',
    code: ''
})
let showLogin = ref(1)
const phoneLogin = () => {
    if (showLogin.value == 1) {
        showLogin.value = 0;
    } else if (showLogin.value == 0) {
        showLogin.value = 1;
    }
}
let loginType = ref(0)
// 二维码登录
const ORorusername = () => {
    if (loginType.value == 1) {
        loginType.value = 0;
    } else if (loginType.value == 0) {
        loginType.value = 1;
    }
}
const goRegister = () => {
    $router.push('/register')
}
const login = () => {

    userStore.login(loginForm.value).then(() => {
        ElMessage({
            message: '登录成功',
            type: 'success',
            plain: true
        })
        setTimeout(() => {
            $router.push('/home')
        }, 500);

    })
    // $router.push('/home')
}
const loginPhone = async()=>{
    let result = await reqPhoneLogin(phoneForm.value)
    console.log(result.data);
    if(result.data.code==200){
        userStore.token = result.data.data
        userStore.userId = (jwtDecode(result.data.data).sub as string)
        localStorage.setItem('token',result.data.data)
        ElMessage({
            message:'登录成功',
            type:'success',
            plain:true
        })
        setTimeout(() => {
            $router.push('/home')
        }, 1000);
    }
}
const getCode = async () => {
    if (phoneForm.value.phone != '') {
        await reqSendCode(phoneForm.value.phone)
    } else {
        ElMessage({
            message: '请输入手机号',
            type: 'warning',
            plain: true
        })
    }
}
</script>

<style scoped lang="scss">
.content {
    .top {
        width: 100%;
        height: 10vh;
        display: flex;
        background-color: green;

        img {
            margin-left: 80px;
        }

        span {
            font-size: 35px;
            font-weight: 700;
            margin-left: 20px;
            line-height: 100px;
        }
    }

    .login {
        width: 100%;
        height: 80vh;

        .el-card {
            position: relative;
            width: 500px;
            left: 55%;
            top: 15%;

            .QR {
                width: 250px;
                height: 250px;
                background-color: blue;
                margin: auto;

                img {
                    width: 100%;
                    height: 100%;
                }
            }

            .card-header {
                display: flex;
                align-items: center;
                justify-content: center;

                span {
                    font-size: 35px;
                    font-weight: 700;
                }
            }

            .el-input {
                width: 250px;
                margin-left: 20px;
            }

            .register {
                margin-top: 10px;
                text-align: center;

                span {
                    cursor: pointer;
                    color: rgb(42, 54, 222);
                }
            }

            .qr {
                display: flex;
                justify-content: space-between;
                cursor: pointer;

                .qr_phone {
                    display: flex;
                    align-items: center;
                }

                .qr_content {
                    float: right;
                    display: flex;
                    align-items: center;
                    margin-bottom: 5px;
                }


            }

            .usernameLogin {
                cursor: pointer;
                text-align: right;

                span {
                    cursor: pointer;
                }
            }

            .logbtn {
                display: flex;
                justify-content: center;

            }
        }
    }
}
</style>