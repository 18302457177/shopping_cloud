<template>
  <div class="main">
    
    <el-menu router=true style="height: 100%;background-color: #545c64;" default-active="/homePage" class="el-menu-vertical-demo" background-color="grey" text-color="white" @open="handleOpen" @close="handleClose">
      <el-menu-item index="/homePage">
        <el-icon><icon-menu /></el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <location />
          </el-icon>
          <span>用户管理</span>
        </template>
        <el-menu-item index="/userList">用户列表</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon>
            <location />
          </el-icon>
          <span>权限管理</span>
        </template>
        <el-menu-item index="/roleList">角色列表</el-menu-item>
        <el-menu-item index="/authList">权限列表</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>
          <el-icon>
            <location />
          </el-icon>
          <span>商品管理</span>
        </template>
        <el-menu-item index="/goodsList">商品列表</el-menu-item>
        <el-menu-item index="/categoryParameter">分类参数</el-menu-item>
        <el-menu-item index="/goodsCategory">商品分类</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="4">
        <template #title>
          <el-icon>
            <location />
          </el-icon>
          <span>订单管理</span>
        </template>
        <el-menu-item index="/orderList">订单列表</el-menu-item>
      </el-sub-menu>
      
      
      <el-sub-menu index="5">
        <template #title>
          <el-icon>
            <location />
          </el-icon>
          <span>数据统计</span>
        </template>
        
      </el-sub-menu>
      
      
    </el-menu>
  </div>
</template>

<script setup lang="ts" name="Aside">

</script>

<style scoped lang="scss">
.main{
  height: 100%;
}
</style>