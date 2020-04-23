<template>
  <el-container>
    <el-aside width="220px">
      <!-- 顶部图片start -->
      <el-image :src="Aurl"></el-image>
      <!-- 顶部图片end -->
      <!-- 导航start -->
      <el-menu
       :default-active="this.$route.path"
        router
        class="el-menu-vertical-demo">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>板块</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/addplate">添加板块</el-menu-item>
            <el-menu-item index="/editplate">编辑板块</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>帖子</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/addArticle">帖子发布</el-menu-item>
            <el-menu-item index="/editArticle"> 帖子管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>用户</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/editUser"> 用户管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>  
      <!-- 导航end -->
    </el-aside>
    <el-container>
      <el-header style="padding:0px;height: 206px">
          <!-- 顶部图片start -->
          <div class="ss_top">
            <el-image style="width: 350px; height: 206px"
              :src="Hurl" fit=""></el-image>
          </div>
          <!-- 顶部图片end -->
      </el-header>
      <el-main>
        <!-- 显示页面start -->
        <router-view></router-view>
        <!-- 显示页面end -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
name:'plate',
data () {
    return {
        Aurl : require('../../assets/image/ssbk/ssbk1.jpg'),
        Hurl : require('../../assets/image/ssbk/ssbk2.jpg'),
        
    }
},
created() {
    
},
methods: {
    
},
}
</script>

<style scoped>
.ss_top{  
  width: 100%;
  display: flex;                        /* 布局格式????? 中间的表单居中了!!! */
  justify-content: center;              /* 页面竖向居中!!! */
  background-color: pink;
}
</style>