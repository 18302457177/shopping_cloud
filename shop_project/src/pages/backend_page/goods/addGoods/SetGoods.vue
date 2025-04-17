<template>
    <div class="main">
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>商品设置</span>
                </div>
            </template>
            <div class="goodsSet">
                <el-form :model="goodsData" label-width="80px">
                    <el-form-item label="商品名称">
                        <el-input v-model="goodsData.name" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="goodsData.description" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item label="上架状态">
                        <el-switch  class="mb-2" />
                    </el-form-item>
                    <el-form-item label="售价">
                        <el-input v-model="goodsData.price" placeholder=""></el-input>
                    </el-form-item>
                </el-form>
                <div class="uploadImg">
                    <span>上传视频</span>
                    <el-upload ref="uploadRef" accept="video/*" action="#" :limit="1" list-type="picture-card"
                        :before-upload="beforeUpload" :on-preview="handlePictureCardPreview" :http-request="uploadVideo"
                        :auto-upload="true">
                        <el-icon>
                            <Plus />
                        </el-icon>
                    </el-upload>
                    <!-- <el-button @click="submitUpload" type="primary">上传</el-button> -->
                </div>
                <div v-show="percentageShow">
                    <el-progress :percentage="percentage" style="width: 100%" />
                </div>
                <div class="uploadImg">
                    <span>上传图片</span>
                    <el-upload ref="uploadRef" v-model:file-list="fileList" accept="image/*" action="#"
                        list-type="picture-card" :on-preview="handlePictureCardPreview" :on-remove="handleRemove"
                        :http-request="uploadImg" :auto-upload="false">
                        <el-icon>
                            <Plus />
                        </el-icon>
                    </el-upload>
                    <el-button @click="submitUpload" type="primary">上传</el-button>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts" name="SetGoods">
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import SparkMD5 from 'spark-md5'
import { ElMessage } from 'element-plus'
import type { UploadProps, UploadRequestOptions, UploadInstance, UploadRawFile } from 'element-plus'
import { reqCheckFile, reqCheckChunk, reqMergeChunks } from '@/api/mediaApi'
import axios from 'axios'
import { useAddGoodsStore } from '@/store/goods/addGoods';
let addGoodsStore = useAddGoodsStore()
const fileList = ref()
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const percentage = ref(0)
const percentageShow = ref(false)
let goodsData = ref({
    id:0,
    name: '',
    description: '',
    saleStatus: '',
    price: '',
    storeId:1
})

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}
const uploadRef = ref<UploadInstance>()
const submitUpload = () => {
    uploadRef.value!.submit()
}
let needUpload = ref(true)
let chunkSize = 1024 * 1024 * 5

const getFileMd5 = (file: any) => {
    return new Promise((resolve, reject) => {
        let fileReader = new FileReader()
        fileReader.onload = function (event) {
            let fileMd5 = SparkMD5.ArrayBuffer.hash(event.target?.result)
            resolve(fileMd5)
        }
        fileReader.readAsArrayBuffer(file)
    })
}
const beforeUpload = async (rawFile: UploadRawFile) => {
    needUpload.value = true
    const fileMd5 = await getFileMd5(rawFile) as string
    let result = await reqCheckFile(fileMd5)
    console.log(result.data.data);
    if (result.data.data) {
        needUpload.value = false
        ElMessage({
            message: '视频已存在，请勿重复上传',
            type: 'error',
            plain: true
        })
    }
}
const uploadVideo = async (options: UploadRequestOptions) => {

    if (!needUpload.value) {
        // console.log(456);
        percentageShow.value = true
        percentage.value = 100
        setTimeout(() => {
            percentageShow.value = false
        }, 2000)
        return
    }
    percentageShow.value = true
    let file = options.file
    let suffix = file.name.split('.')
    addGoodsStore.addGoodsDto.videoSuffix = suffix[1]
    let totalChunks = Math.ceil(file.size / chunkSize)
    let mergeVo = {
        fileMd5: undefined as string | undefined,
        fileName: file.name,
        chunkTotal: totalChunks
    }
    let md5 = await getFileMd5(file) as string
    addGoodsStore.addGoodsDto.videoMd5 = md5
    for (let i = 0; i < totalChunks; i++) {
        let start = i * chunkSize
        let end = Math.min(start + chunkSize, file.size)
        let chunk = file.slice(start, end)

        let formData = new FormData();
        formData.append('file', chunk)
        formData.append('fileMd5', md5)
        formData.append('chunk', i.toString())
        try {
            // console.log(formData);
            let isExistResult = await reqCheckChunk({
                'fileMd5': md5,
                'chunk': i.toString()
            })

            let isExist = isExistResult.data.data
            console.log(isExist);
            if (!isExist) {
                // let chunkResult = await reqUploadChunk(formData)
                // console.log(chunkResult.data);
                axios({
                    url: '/api/media/upload/uploadChunk',
                    method: 'post',
                    headers: { 'Content-Type': 'multipart/form-data' },
                    data: formData,
                    // transformRequest: [function (data: any, headers: any) {
                    //     // 去除post请求默认的Content-Type
                    //     delete headers.post['Content-Type']
                    //     return data
                    // }],
                }).then((res) => {
                    if (res.data.data) {
                        percentage.value = i / totalChunks * 100

                    }
                })

            }
        } catch (error) {
            console.log("上传分块失败");
        }
    }
    let isUploadSucess = true
    if (isUploadSucess) {
        mergeVo.fileMd5 = md5 as string
        setTimeout(async () => {
            let mergeResult = await reqMergeChunks(mergeVo)
            if (mergeResult.data.code == 200) {
                percentage.value = 100
                ElMessage({
                    message: '上传成功',
                    type: 'success',
                    plain: true
                })
            } else {
                ElMessage({
                    message: '合并文件失败',
                    type: 'error',
                    plain: true
                })
            }
        }, 3000);

    }

}
const uploadImg = async (options: UploadRequestOptions) => {
    let file = options.file
    console.log(file);
    let md5 = await getFileMd5(file) as string
    addGoodsStore.addGoodsDto.imgMd5 = md5
    let suffix = file.name.split('.')
    addGoodsStore.addGoodsDto.imgSuffix = suffix[1]
    let formData = new FormData();
    // console.log(md5);
    // console.log(file);
    formData.append('file', file)
    formData.append('fileMd5', md5)
    axios({
        url: '/api/media/upload/img',
        method: 'post',
        headers: { 'Content-Type': 'multipart/form-data' },
        data: formData,
        // transformRequest: [function (data: any, headers: any) {
        //     // 去除post请求默认的Content-Type
        //     delete headers.post['Content-Type']
        //     return data
        // }],
    }).then((res) => {
        
    }).catch((error)=>{
        ElMessage({
            message:'图片上传失败',
            type:'error',
            plain:true
        })
    })
}
const saveGoodsToStore = ()=>{
    addGoodsStore.addGoodsDto.goodsData = goodsData.value
}
defineExpose({saveGoodsToStore})
</script>

<style scoped lang="scss">
.main {
    margin-top: 7px;

    .el-card {
        .goodsSet {
            .uploadImg {
                display: flex;
                align-items: center;

                span {
                    margin-right: 13px;
                    margin-left: 12px;
                    font-size: 14px;
                    color: rgb(107, 99, 99);
                }
            }
        }
    }
}
</style>