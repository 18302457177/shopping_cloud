import request from "@/util/request";

// export const reqLogout = () => request.post<any,any>('/user/logout')
export const reqRegister = (data:any) => request.post<any,any>('/user/register',data)
export const reqUserInfo = (id:string)=>request.get<any,any>(`/user/getInfo/${id}`)
export const reqEditUser = (data:any)=>request.post<any,any>('/user/editUser',data)
export const reqChangeDefaultAddr = (userId:number|string,id:number)=>request.post<any,any>(`/user/changeDefaultAddr/${userId}/${id}`)


export const reqLogin = (data:any) => request.post<any,any>('/api/user/login',data)
export const reqSendCode = (phone:any) => request.get<any,any>(`/api//user/message/send/${phone}`)
export const reqPhoneLogin = (data:any)=>request.post<any,any>('api/user/phoneLogin',data)
export const reqLogout = (id:string)=>request.post<any,any>(`/api/user/logout/${id}`)
export const reqUserPage = (storeId:string|number,pageNo:number,pageSize:number)=>request.get<any,any>(`/api/user/pageUser/${storeId}/${pageNo}/${pageSize}`)
export const reqDeleteUser = (storeId:string|number,userId:number)=>request.delete<any,any>(`/api/user/delete/${storeId}/${userId}`)
export const reqChangeUserStatus = (data:any)=>request.post<any,any>('/api/user/changeUserStatus',data)
export const reqUserRoleList = (storeId:string|number)=>request.get<any,any>(`/api/user/getRoleList/${storeId}`)
export const reqAddRole = (data:any)=>request.post<any,any>('/api/user/addRole',data)
export const reqSearchUser = (data:any)=>request.post<any,any>('/api/user/searchUser',data)
export const reqAddStoreUser = (data:any)=>request.post<any,any>('/api/user/addStoreUser',data)
export const reqRolePage = (storeId:number,pageNo:number,pageSize:number)=>request.get<any,any>(`/api/user/pageRole/${storeId}/${pageNo}/${pageSize}`)