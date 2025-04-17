<template>
    <div class="main">
        <el-card>
            <div class="top">
                <el-button type="primary" @click="dialogVisible = true">添加角色</el-button>
            </div>
            <div class="list">
                <el-table :data="userRoleList" border>
                    <el-table-column type="index" width="50" />
                    <el-table-column prop="role" label="角色名称" />
                    <el-table-column prop="description" label="角色描述" />
                    <el-table-column prop="操作" label="操作">
                        <template #="{ row }">
                            <el-button @click="editRole(row)" type="primary" icon="Edit"></el-button>
                            <el-button type="primary"></el-button>
                            <el-button type="primary"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize"
                    :page-sizes="[5, 10, 15, 20]" 
                    layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="getRolePageList"
                    @current-change="getRolePageList" />
            </div>
        </el-card>
        <el-dialog v-model="dialogVisible" title="添加角色" width="500">
            <el-card style="width: 420px;">
                <el-form :model="form" label-width="auto" style="max-width: 600px">
                    <el-form-item label="角色名称">
                        <el-input v-model="form.role" />
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="form.description" type="textarea" />
                    </el-form-item>
                </el-form>
            </el-card>
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

<script setup lang="ts" name="RoleList">
import { reqUserRoleList, reqAddRole,reqRolePage } from '@/api/useApi';
import { onMounted, ref } from 'vue';
let userRoleList = ref([])
let dialogVisible = ref(false)
let form = ref({
    id: 0,
    role: '',
    description: '',
    storeId: 1
})
onMounted(() => {
    getRolePageList()
})
// const getUserRoleList = async () => {
//     let result = await reqUserRoleList("1")
//     console.log(result.data);
//     if (result.data.code == 200) {
//         userRoleList.value = result.data.data;
//     }
// }
const submit = async () => {
    let result = await reqAddRole(form.value)
    console.log(result.data);
}
const editRole = (row:any)=>{
    
}
let pageNo = ref(1)
let pageSize = ref(5)
let total = ref()
const getRolePageList = async()=>{
    let result = await reqRolePage(1,pageNo.value,pageSize.value)
    // console.log(result.data);
    if(result.data.code==200){
        userRoleList.value = result.data.data.data
        total.value = result.data.data.total
    }
}
</script>

<style scoped lang="scss">
.main {
    .el-card {
        margin-left: 20px;
        margin-right: 30px;

        .list {
            margin-top: 20px;
        }

        .pagination {
            margin-top: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
}
</style>