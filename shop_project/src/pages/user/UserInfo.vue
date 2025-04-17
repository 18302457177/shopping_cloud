<template>
    <div class="container">
        <el-card shadow="hover">
            <el-descriptions title="个人信息" border>
                <el-descriptions-item :rowspan="2" :width="140" label="头像" align="center">
                    <el-image style="width: 100px; height: 100px" :src="userStore.avatar" />
                </el-descriptions-item>
                <el-descriptions-item label="昵称">
                    {{ userInfo.nickname }}
                </el-descriptions-item>
                <el-descriptions-item label="性别">
                    <el-tag size="small">{{ userInfo.sex == 0 ? '男' : '女' }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="手机号码">{{ userInfo.phone }}</el-descriptions-item>
                <el-descriptions-item label="个性签名">{{ userInfo.signature }}</el-descriptions-item>
                <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
                <el-descriptions-item label="地区">{{ userInfo.area }}</el-descriptions-item>
                <el-descriptions-item label="生日">{{ userInfo.birthday }}</el-descriptions-item>

                <el-descriptions-item label="收获地址">
                    {{ userInfo.defaultAddr.address }}
                </el-descriptions-item>
            </el-descriptions>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="UserInfo">
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
let userStore = useUserStore()
let userInfo = ref(userStore.userInfo)
</script>

<style scoped lang="scss">
.container {
    display: flex;
    justify-content: center;


    .el-card {
        width: 800px;
    }
}
</style>