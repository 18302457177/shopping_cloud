import request from "@/util/request";
export const reqOrder = (id:string) => request.get<any,any>(`/order/getOrder/${id}`)
export const reqDeleteOrder = (id:string,orderId:string) => request.delete<any,any>(`/order/deleteOrder/${id}/${orderId}`)
export const reqOrderPage = (id:string,pageNo:string|number,pageSize:string|number) => request.get<any,any>(`/order/page/${id}/${pageNo}/${pageSize}`)
export const reqSearchOrder = (orderId:number|string) => request.get<any,any>(`/order/search/${orderId}`)
export const reqApply = (data:any) => request.post<any,any>('/order/apply',data)


export const reqGetStoreOrder=(storeId:any,pageNo:any,pageSize:any)=>request.get<any,any>(`/api/order/getStoreOrder/${storeId}/${pageNo}/${pageSize}`)
export const reqQueryOrder=(orderSn:any)=>request.get<any,any>(`/api/order/queryOder/${orderSn}`)