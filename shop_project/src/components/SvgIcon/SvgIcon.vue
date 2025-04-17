<!-- 
  封装svg组件
    参数1：name（必传）
    参数2：color（默认为svg原本颜色）
    参数3：width（默认16px）
    参数4：height（默认16px）
 -->
 <template>
  <svg :style="{ width,height }">
   <use :href="prefix+name" :fill="color" ></use>
  </svg>
</template>
 
<script setup lang="ts">
 
// 接收父组件传递过来的参数
defineProps({
prefix:{
  type:String,
  default:'#icon-'
},
name:String,
color:{
  type:String,
  default:''
},
width:{
  type:String,
  default:'16px'
},
height:{
  type:String,
  default:'16px'
}
})
 
</script>
 
<style scoped>
 
</style>