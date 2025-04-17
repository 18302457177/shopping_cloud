<template>
    <Header></Header>
    <Logo></Logo>
    <div class="main">
        <div class="top">
            <el-card shadow="hover">
                <div class="top-card">
                    <div class="block">
                        <el-avatar :size="110" :src="userStore.avatar" />
                        <span class="username">{{ userInfo.nickname }}</span>
                    </div>
                    <div class="edit">
                        <el-button type="primary" @click="dialogVisible = true">编辑</el-button>
                    </div>
                </div>
            </el-card>
        </div>
        <div class="main-content">
            <div class="left">
                <el-menu  class="el-menu-vertical-demo" router=true default-active=/user/userInfo>
                    <el-menu-item index="/user/userInfo">
                        <el-icon>
                            <setting />
                        </el-icon>
                        <span >个人信息</span>
                    </el-menu-item>
                    <el-menu-item index="/user/coupons">
                        <el-icon>
                            <setting />
                        </el-icon>
                        <span>优惠卷</span>
                    </el-menu-item>
                </el-menu>
            </div>
            <div class="bottom">
                <div class="bottom-card">
                        <RouterView></RouterView>
                </div>
            </div>
        </div>

        <el-dialog v-model="dialogVisible" title="修改个人信息" width="500">
            <div class="avatar-div">
                <el-upload ref="uploadRef" :data="ruleForm" class="avatar-uploader"
                    action="http://localhost:8080/upload/img" :show-file-list="false" :auto-upload=false
                    :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
            </div>
            <el-form ref="ruleFormRef" style="max-width: 600px" :model="ruleForm" :rules="rules" label-width="80"
                status-icon>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="ruleForm.nickname" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="ruleForm.sex">
                        <el-radio value="0" size="large">男</el-radio>
                        <el-radio value="1" size="large">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号码" prop="">
                    <el-input v-model="ruleForm.phone" />
                </el-form-item>
                <el-form-item label="邮箱" prop="">
                    <el-input v-model="ruleForm.email" />
                </el-form-item>
                <el-form-item label="个性签名" prop="">
                    <el-input v-model="ruleForm.signature" />
                </el-form-item>
                <el-form-item label="地区" prop="">
                    <el-input v-model="ruleForm.area" />
                </el-form-item>
                <!-- <el-form-item label="生日" prop="">
                    <el-input v-model="ruleForm.birthday" />
                </el-form-item> -->
                <el-form-item label="收获地址" prop="">
                    <el-input v-model="ruleForm.address" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submit">
                        提交
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts" name="User">
import { onMounted, reactive, ref } from 'vue';
import Header from '@/components/Header.vue';
import Logo from '@/components/Logo.vue';
import { useUserStore } from '@/store/user';
import { ElMessage } from 'element-plus';
import type { UploadProps, UploadInstance } from 'element-plus'
import { useRouter } from 'vue-router';
let $router = useRouter()
let userStore = useUserStore()
let userInfo = ref(userStore.userInfo)
const uploadRef = ref<UploadInstance>()
let dialogVisible = ref(false)
let ruleForm = reactive({
    id: userStore.userId,
    nickname: '',
    sex: '',
    phone: '',
    email: '',
    signature: '',
    area: '',
    // birthday: '',
    address: ''
})

const submit = async () => {
    uploadRef.value!.submit()
    // let result = await reqEditUser(ruleForm.value)
    // if (result.data.code == 200) {
    //     dialogVisible.value = false
    //     ElMessage({
    //         type: 'success',
    //         message: '修改成功',
    //         plain: true
    //     })
    // } else {
    //     dialogVisible.value = false
    //     ElMessage({
    //         type: 'error',
    //         message: '修改失败',
    //         plain: true
    //     })
    // }
}

const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    imageUrl.value = URL.createObjectURL(uploadFile.raw!)
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.type !== 'image/jpeg') {
        ElMessage.error('Avatar picture must be JPG format!')
        return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('Avatar picture size can not exceed 2MB!')
        return false
    }
    return true
}


const rules = ({})
</script>
<style scoped>
.avatar-uploader .avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    display: block;
}
</style>
<style scoped lang="scss">
.main {

    .top {
        display: flex;
        justify-content: center;

        .el-card {
            width: 1000px;
            height: 150px;

            .top-card {
                display: flex;
                justify-content: space-between;

                .block {
                    .username {
                        position: relative;
                        top: -20px;
                        margin-left: 20px;
                    }
                }
            }

        }
    }

    .main-content {
        display: flex;

        .left {
            position: relative;
            left: 17.3%;
            margin-top: 15px;
            background-color: red;
            width: 12%;
            height: 300px;
        }

        .bottom {
            margin-top: 15px;
            position: relative;
            left: 18.3%;

            
        }
    }

    .avatar-div {
        display: flex;
        justify-content: center;

        .avatar-uploader .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .avatar-uploader .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 50px;
            height: 50pxpx;
            text-align: center;
        }
    }
}
</style>
