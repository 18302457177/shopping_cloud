<template>
    <div class="main">
        <el-card>
            <div class="list">
                <el-table border>
                    <el-table-column type="index" width="50" />
                    <el-table-column prop="姓名" label="权限名称" />
                    <el-table-column prop="邮箱" label="路径" />
                    <el-table-column prop="操作" label="权限等级">
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="AuthList">

</script>

<style scoped lang="scss">
.main{
    .el-card{
        margin-left: 20px;
        margin-right: 30px;
    }
}
</style>