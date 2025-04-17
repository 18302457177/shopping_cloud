import request from "@/util/request";
export const reqCheckFile = (fileMd5:any)=>request.post<any,any>(`/api/media/upload/checkFile/${fileMd5}`)
export const reqCheckChunk = (param:any)=>request.post<any,any>('/api/media/upload/checkChunk',param)
export const reqUploadChunk = (data:any)=>request({
    url:'/api/media/upload/uploadChunk',
    method:'post',
    headers:{'Content-Type': 'multipart/form-data'},
    data:data,
    transformRequest: [function(data, headers) {
        // 去除post请求默认的Content-Type
        delete headers.post['Content-Type']
        return data
      }],
})
// export const reqUploadChunk = (param1:any)=>request.post<any,any>('/api/media/upload/uploadChunk',param1)
export const reqMergeChunks = (param:any)=>request.post<any,any>('/api/media/upload/mergeChunks',param)