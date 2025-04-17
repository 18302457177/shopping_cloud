import SvgIcon from "./SvgIcon/SvgIcon.vue";

const allGloablCom = {SvgIcon}

export default {
    // @ts-ignore
    install(app){
        Object.keys(allGloablCom).forEach(key=>{
            // @ts-ignore
            app.component(key,allGloablCom[key])
        })
    }
}