import request from "@/util/request";

export const reqCart = (id:string) => request.get<any,any>(`/cart/getCart/${id}`)
export const reqPage = (id:string,pageNo:number,pageSize:number) => request.get<any,any>(`/cart/page/${id}/${pageNo}/${pageSize}`)
export const reqDelCart = (id:string,cartid:string) => request.delete<any,any>(`/cart/deleteCart/${id}/${cartid}`)
// export const reqCartCount = (id:number,count:number) => request.put<any,any>(`/cart/cartCount/${id}/${count}`)
export const reqSubmitCart = (data:any) => request.post<any,any>('/cart/submitCart',data)
// export const reqAddCart = (data:any) => request.post<any,any>('/cart/addCart',data)


export const reqAddCart = (data:any) => request.post<any,any>('/api/cart/addCart',data)
export const reqGetCart = (userId:any)=> request.get<any,any>(`/api/cart/getCart/${userId}`)
export const reqGetOwnCart = (userId:any,pageNo:any,pageSize:any)=>request.get<any,any>(`/api/cart/getUserOwnCart/${userId}/${pageNo}/${pageSize}`)
export const reqUpdateCartCount = (cartId:any,count:any)=>request.post<any,any>(`/api/cart/updateCartCount/${cartId}/${count}`)
export const reqDeleteCart = (userId:any,cartId:any)=>request.delete<any,any>(`/api/cart/deleteCart/${userId}/${cartId}`)
export const reqDeleteBatchCart = (cartIds:any)=>request.post<any,any>('/api/cart/deleteCartList',cartIds);