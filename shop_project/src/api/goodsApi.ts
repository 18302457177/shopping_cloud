import request from "@/util/request";
// export const reqGoods = () => request.get<any,any>('/goods/getGoods')
export const reqSearchGoods = (data:any) => request.post<any,any>('/goods/search',data)
export const reqGoodDetail = (id:number|string) => request.get<any,any>(`/goods/detail/${id}`)
export const reqGoodCategories = (id:number|string) => request.get<any,any>(`/goods/categories/${id}`)


// 店铺后台接口
export const reqFirstCategory = (storeId:string|number) => request.get<any,any>(`/api/goods/getFirstCategory/${storeId}`)
export const reqSecondCategory = (storeId:string|number,parentId:string|number)=>request.get<any,any>(`/api/goods/getSecondCategory/${storeId}/${parentId}`)
export const reqCategories = (storeId:string|number)=>request.get<any,any>(`/api/goods/getCategories/${storeId}`)
export const reqStaticCategories = (storeId:string|number,category3Id:string|number)=>request.get<any,any>(`/api/goods/getStaticCategory/${storeId}/${category3Id}`)
export const reqDynamicCategories = (storeId:string|number,category3Id:string|number)=>request.get<any,any>(`/api/goods/getDynamic/${storeId}/${category3Id}`)
export const reqGetAllSpu = (storeId:string|number)=>request.get<any,any>(`/api/goods/getAllGoods/${storeId}`)
export const reqAddGoods = (data:any)=>request.post<any,any>('/api/goods/addGoods',data)
export const reqGoodsPage = (storeId:any,pageNo:any,pageSize:any)=>request.get<any,any>(`/api/goods/getGoodsPage/${storeId}/${pageNo}/${pageSize}`)
export const reqDeleteSpu = (spuId:any)=>request.delete<any,any>(`/api/goods/deleteSpu/${spuId}`)
// 前台接口
export const reqGetGoods = ()=>request.get<any,any>('/api/goods/frontGoods/getGoods')
export const reqGetGoodsDetail = (spuId:any)=>request.get<any,any>(`/api/goods/frontGoods/getGoodsDetail/${spuId}`)