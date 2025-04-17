<template>
    <div class="main">
        <el-card class="card">
            <div class="top">
                <el-input v-model="searchData.name" placeholder="请输入内容" @keyup.enter="searchUser"></el-input>
                <el-button class="search" icon="Search" @click="searchUser"></el-button>
                <el-button @click="addStoreUser" class="btn" type="primary">添加用户</el-button>
            </div>
            <div class="list">
                <el-table :data="userList" border>
                    <el-table-column type="index" width="50" />
                    <el-table-column prop="name" label="姓名" />
                    <el-table-column prop="email" label="邮箱" />
                    <el-table-column prop="phone" label="电话" />
                    <el-table-column prop="role" label="角色">
                        <template #="{ row }">
                            <el-tag style="margin-right: 5px;" type="success" v-for="role in row.role">{{ role
                            }}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="状态">
                        <template #="{ row }">
                            <el-switch v-model="row.status" class="ml-2" inline-prompt :active-value="0"
                                :inactive-value="1"
                                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" active-text="启用"
                                inactive-text="禁用" @change="changeSwitch(row)" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="操作" label="操作">
                        <template #="{ row }">
                            <el-button type="primary" icon="Edit"></el-button>
                            <el-button @click="deleteUser(row.id)" type="danger" icon="Delete"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize" :page-sizes="[5, 10, 15, 20]"
                    :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="changePage" @current-change="changePage" />
            </div>
        </el-card>
        <el-dialog v-model="dialogVisible" title="添加用户" width="500" :before-close="handleClose">
            <div class="addUser">
                <el-form ref="form" :model="addUserFormData" label-width="80px">
                    <el-form-item label="用户名">
                        <el-input v-model="addUserFormData.username" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="addUserFormData.password" placeholder="请输入密码"></el-input>
                    </el-form-item>
                </el-form>
            </div>
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

<script setup lang="ts" name="UserList">
import { reqUserPage, reqDeleteUser, reqChangeUserStatus, reqSearchUser,reqAddStoreUser } from '@/api/useApi';
import { useUserStore } from '@/store/user/user';
import { ElMessage } from 'element-plus'
import { onMounted, ref } from 'vue';
let userStore = useUserStore()
let userList = ref([])
onMounted(async () => {
    changePage()
})
let switchValue = ref(1)
let pageNo = ref(1)
let pageSize = ref(5)
let total = ref()
const changePage = async () => {

    let result = await reqUserPage("1", pageNo.value, pageSize.value)
    // console.log(result.data);
    if (result.data.code == 200) {
        userList.value = result.data.data.data
        total.value = result.data.data.total

    }
}
const changeSwitch = (row: any) => {
    console.log(row);
    reqChangeUserStatus(row)
}
const deleteUser = async (userId: number) => {
    let result = await reqDeleteUser(1, userId)
    if (result.data.code == 200) {

        ElMessage({
            message: '删除成功',
            type: 'success',
            plain: true
        })
        setTimeout(() => {
            location.reload();
        }, 1000)
    }
}
let searchData = ref({
    storeId:'1',
    name:''
})
const searchUser = async () => {
    if (searchData.value.name != '') {
        let result = await reqSearchUser(searchData.value)
        if (result.data.code == 200) {
            console.log(result.data.data);
            userList.value = []
            userList.value.push(result.data.data)
            searchData.value = {
                storeId:'1',
                name:''
            }
        }
    } else {
        changePage()
    }
}
let dialogVisible = ref(false)
let addUserFormData = ref({
    username:'',
    password:'',
    storeId:'1'
})
const handleClose = ()=>{
    addUserFormData.value.username = ''
    addUserFormData.value.password = ''
}
const addStoreUser = ()=>{
    dialogVisible.value = true
}
const submit = async()=>{
    let result = await reqAddStoreUser(addUserFormData.value)
    if(result.data.code == 200){
        setTimeout(()=>{
            dialogVisible.value = false
        },500)
        ElMessage({
            message: '添加成功',
            type: 'success',
            plain: true
        })
    }else{
        ElMessage({
            message: '添加失败',
            type: 'error',
            plain: true
        })
    }
}
</script>

<style scoped lang="scss">
.main {
    .card {
        margin-left: 20px;
        margin-right: 30px;

        .top {
            .el-input {
                margin-left: 20px;
                width: 350px;
                height: 40px;
            }

            .search {
                height: 40px;
                width: 50px;
            }

            .btn {
                margin-left: 20px;
            }
        }

        .list {
            .el-table {
                margin-top: 20px;
                margin-left: 20px;
                margin-right: 20px;
            }
        }

        .pagination {
            margin-top: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
}
</style>