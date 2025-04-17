<template>
    <div class="common-layout">
        <el-container>
            <el-header style="padding: 0;">
                    <BackHeader></BackHeader>
                </el-header>
            <el-container>
                <el-aside width="200px" style="height: 100%;">
                <Aside></Aside>
            </el-aside>
                <el-main>
                    <BreadCrumb></BreadCrumb>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup lang="ts" name="Layout">
import Aside from '@/components/backend_business/aside/Aside.vue'
import BackHeader from './header/BackHeader.vue';
import BreadCrumb from './main/breadCrumb/BreadCrumb.vue';
</script>

<style scoped lang="scss">
.common-layout {
    height: 100%;
    position:absolute;
    top:0;
    right:0;
    bottom:0;
    left:0;

    .el-container {
        height: 100%;
        
        
    }
}
</style>