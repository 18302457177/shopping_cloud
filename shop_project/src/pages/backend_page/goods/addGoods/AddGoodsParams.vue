<template>
    <div class="main">
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>添加商品参数</span>
                </div>
            </template>
            <div class="addParams">
                <div class="addbtn">
                    <el-button @click="addParam" type="primary">添加参数</el-button>
                </div>
                <div class="addInput">
                    <el-table :data="skuList" height="250" style="width: 100%">
                        <el-table-column type="index" label="序号" width="100" />
                        <el-table-column prop="skuName" label="参数" width="200">
                            <template #="{ row }">
                                <el-input v-model="skuData.skuName" @blur="saveSku" v-if="row.skuInput"
                                    placeholder="请输入参数名"></el-input>
                                <span v-else>{{ row.skuName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="param" label="值">
                            <template #="{ row }">
                                <el-input v-if="row.paramInput" ref="InputRef" v-model="paramData" style="width: 200px;"
                                    class="w-20" size="small" @keyup.enter="saveParam(row)" @blur="saveParam(row)" />
                                <!-- <el-input @blur="saveParam(row)" v-model="paramData" v-if="row.paramInput"
                                    placeholder="请输入参数值"></el-input> -->
                                <div v-else>
                                    <el-tag v-for="item1 in row.param" @close="deleteTag(row, item1)" closable
                                        type="success">{{ item1
                                        }}</el-tag>
                                    <el-button class="button-new-tag" size="small" @click="addOtherParam(row)">
                                        +
                                    </el-button>
                                    <!-- <el-button @click="addOtherParam(row)" type="primary"
                                        size="small">点击添加参数</el-button> -->
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="300">
                            <template #="{ row }">
                                <el-button type="danger" icon="Delete"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="AddGoodsParams">
import { ref } from 'vue';
import { useAddGoodsStore } from '@/store/goods/addGoods';
let addGoodsStore = useAddGoodsStore()
let skuList = ref([])
let skuData = ref({
    skuName: '',
    param: [],
    skuInput: false,
    paramInput: false
})
const saveSku = () => {
    if (skuData.value.skuName != '') {
        skuList.value.forEach(element => {
            // console.log(element);
            if (element.skuName == skuData.value.skuName) {
                element.skuName = skuData.value.skuName
                element.skuInput = false
                // console.log(element.input);
            }
        });
    }
    skuData.value = {
        skuName: '',
        param: [],
        skuInput: false,
        paramInput: true
    }
}
let paramData = ref('')
const saveParam = (data: any) => {
    skuList.value.forEach(item => {
        if (item.skuName == data.skuName && paramData.value != '') {
            item.param.push(paramData.value)
            item.paramInput = false
        }
    })

    paramData.value = ''
}
const addParam = () => {
    // 记录空值个数
    let num = 0
    skuList.value.forEach(element => {
        if (element.skuName == '' || element.param.length == 0) {
            num = num + 1
        }
    });
    skuData.value.skuInput = true
    skuData.value.paramInput = true
    // console.log(num);
    if (num == 0) {
        // console.log(skuData.value);
        skuList.value.push(skuData.value)
    }
}
const addOtherParam = (data: any) => {
    skuList.value.forEach(item => {
        if (item.skuName == data.skuName) {
            item.paramInput = true
        }
    })

}
const deleteTag = (row: any, param: any) => {
    skuList.value.forEach(element => {
        if (element.skuName == row.skuName) {
            for (let i = 0; i < element.param.length; i++) {
                if (element.param[i] == param) {
                    element.param.splice(i)
                }
            }
        }
    });
}
const saveSkuToStore = () => {
    let skuListFinal: any = []
    skuList.value.forEach(element => {
        let sku = {
            skuName: '',
            param: []
        }
        sku.skuName = element.skuName
        element.param.forEach(item => {
            sku.param.push(item)
        });
        skuListFinal.push(sku)
    });
    console.log(skuListFinal);
    // console.log(123);
    addGoodsStore.addGoodsDto.skuList = skuListFinal
}
defineExpose({ saveSkuToStore })
</script>

<style scoped lang="scss">
.main {
    margin-top: 7px;
}
</style>