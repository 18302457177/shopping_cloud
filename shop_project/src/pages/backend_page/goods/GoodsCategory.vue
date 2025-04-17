<template>
    <div class="main">
        <el-card>
            <div class="add">
                <el-button type="primary" @click="addCategory">添加分类</el-button>
            </div>
            <div class="list">
                <el-table :data="categories" style="width: 100%" row-key="id" border>
                    <el-table-column type="index" label="index" width="70" />
                    <el-table-column prop="name" label="分类名称" />
                    <el-table-column prop="sort" label="排序" />
                    <el-table-column label="操作">
                        <template #="{ row }">
                            <el-button type="primary" icon="Edit"></el-button>
                            <el-button type="danger" icon="Delete"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                    :page-sizes="[100, 200, 300, 400]" :size="size" :disabled="disabled" :background="background"
                    layout="total, sizes, prev, pager, next, jumper" :total="400" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" />
            </div>
        </el-card>
        <el-dialog v-model="dialogVisible" title="添加分类" width="500" :before-close="handleClose">
            <el-form ref="form" :model="categoryForm" label-width="80px">
                <el-form-item label="一级分类">
                    <el-input v-model="categoryForm.category1" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="二级分类">
                    <el-input v-model="categoryForm.category2" placeholder=""></el-input>
                </el-form-item>
                <el-form-item label="三级分类">
                    <el-input v-model="categoryForm.category3" placeholder=""></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="dialogVisible = false">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts" name="GoodsCategory">
import { reqCategories } from '@/api/goodsApi';
import { onMounted, ref } from 'vue';
let categories = ref([])
let addGoodsShow = ref(false)
let dialogVisible = ref(true)
let categoryForm = ref({
    category1:'',
    category2:'',
    category3:''
})
onMounted(async () => {
    let result = await reqCategories(1)
    console.log(result.data);
    if (result.data.code == 200) {
        categories.value = result.data.data
    }
})

</script>

<style scoped lang="scss">
.main {
    .el-card {
        margin-left: 20px;
        margin-right: 30px;

        .pagination {
            margin-top: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
}
</style>