<template>
    <div class="content">
        <div class="register">
            <el-card>
                <template #header>
                    <div class="card-header">
                        <span>注册账号</span>
                    </div>
                </template>
                <el-form label-width="100px" :rules="rules" :model="registerForm">
                    <el-form-item label="姓名:" prop="name">
                        <el-input v-model="registerForm.name" />
                    </el-form-item>
                    <el-form-item label="用户名:" prop="username">
                        <el-input v-model="registerForm.username" />
                    </el-form-item>
                    <el-form-item label="密码:" prop="password1">
                        <el-input v-model="registerForm.password1" />
                    </el-form-item>
                    <el-form-item label="确认密码:" prop="password2">
                        <el-input v-model="registerForm.password2"/>
                    </el-form-item>
                    <el-form-item label="手机号码:" prop="phone">
                        <el-input v-model="registerForm.phone" />
                    </el-form-item>
                    <el-form-item label="邮箱:" prop="email">
                        <el-input v-model="registerForm.email" />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="bottom">
                        <div class="left">
                            <el-button @click="register" type="primary" size="large">提交</el-button>
                        </div>
                        <div class="right">
                            <el-button @click="goLogin" type="primary" size="large">取消</el-button>
                        </div>
                    </div>
                </template>
            </el-card>
        </div>
    </div>
</template>

<script setup lang="ts" name="Register">
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { reqRegister } from '@/api/useApi';
import { ElMessage } from 'element-plus';
import type { UploadProps,FormRules } from 'element-plus'

let $router = useRouter()
let registerForm = reactive({
    name: '',
    username: '',
    password1: '',
    password2: '',
    phone: '',
    email: ''
})

const goLogin = () => {
    $router.push('/login')
}
// const register = async () => {
//     uploadRef.value!.submit()
//     let result = await reqRegister(registerForm)
//     if (result.data.code == 200) {
//         ElMessage({
//             type: 'success',
//             message: '注册成功',
//             plain: true
//         })
//         $router.push('/login')
//     } else {
//         ElMessage({
//             type: 'error',
//             message: '注册失败',
//             plain: true
//         })
//     }
// }
// 校验姓名
const validateName = (rule: any, value: any, callback: any)=>{
    let reg = /^[\u4E00-\u9FA5]{2,10}(·[\u4E00-\u9FA5]{2,10}){0,2}$/
    if(value === ''){
        callback(new Error('请输入姓名'))
    }
    if(!reg.test(value)){
        callback(new Error('请输入2-10位的汉字'))
    }
    callback()
}
// 校验用户名
const validateUsername = (rule: any, value: any, callback: any)=>{
    let reg = /^[\w\u4E00-\u9FA5]{4,20}$/
    if(value === ''){
        callback(new Error('请输入用户名'))
    }
    if(!reg.test(value)){
        callback(new Error('用户名 2-20位,只能包含汉字/数字/字母和下划线'))
    }
    callback()
}
// 密码校验
const validatePassword1 = (rule: any, value: any, callback: any)=>{
    let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?.&_-])[\w$@$!%*?.&-]{8,16}/;
    if(value === ''){
        callback(new Error('请输入密码'))
    }
    if(!reg.test(value)){
        callback(new Error('密码 8-16位,至少1个大写字母,1个小写字母,1个数字和1个特殊字符'))
    }
    callback()
}
const validatePassword2 = (rule: any, value: any, callback: any)=>{
    if(value === ''){
        callback(new Error('请重新输入密码'))
    }
    if(value != registerForm.password1){
        callback(new Error('密码不一致'))
    }
    callback()
}
// 校验手机号码
const validatePhone = (rule: any, value: any, callback: any)=>{
    let reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    if(value === ''){
        callback(new Error('请输入手机号码'))
    }
    if(!reg.test(value)){
        callback(new Error('手机号码格式不符'))
    }
    callback()
}
// 校验邮箱
const validateEmail = (rule: any, value: any, callback: any)=>{
    let reg = /^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$/
    if(value === ''){
        callback(new Error('请输入邮箱'))
    }
    if(!reg.test(value)){
        callback(new Error('邮箱格式不符'))
    }
    callback()
}
// 校验规则
const rules = reactive<FormRules>({
    name:[
        {required:true,validator:validateName,trigger:'blur'}
    ],
    username:[
        {required:true,validator:validateUsername,trigger:'blur'}
    ],
    password1:[
        {required:true,validator:validatePassword1,trigger:'blur'}
    ],
    password2:[
        {required:true,validator:validatePassword2,trigger:'blur'}
    ],
    phone:[
        {required:true,validator:validatePhone,trigger:'blur'}
    ],
    email:[
        {required:true,validator:validateEmail,trigger:'blur'}
    ],
})
</script>

<style scoped lang="scss">
.content {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

    .register {
        position: relative;
        top: 50px;
        .card-header {
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 40px;
            font-weight: 700;
        }

        .el-form {
            width: 450px;
            height: 330px;

            .el-input {
                height: 40px;
                width: 300px;
            }
        }

        .bottom {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 60px;

            .el-button {
                width: 120px;
            }
        }
    }
}
</style>
