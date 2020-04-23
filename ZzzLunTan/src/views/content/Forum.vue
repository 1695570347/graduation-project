<template>
  <div>
    <el-container class="center">
      <el-header>
        <div style="width:1500px;padding-left: 200px;">
          <span class="syLogo">论坛</span>
          <el-avatar :size="50" style="float:right;margin:5px;" :src="user.userImage"></el-avatar>
          <!-- <el-button type="warning" class="topMy" icon="el-icon-s-home" @click="myself()">我</el-button> -->
          <el-button type="danger" class="topMy" icon="el-icon-close" @click="logout()">注销</el-button>
        </div>
      </el-header>
      <el-main>
        <!-- 轮播图 start -->
        <!-- <div style="width:1500px"> -->
        <el-row style="width:1500px;">
          <el-col :span="14">
            <el-card class="lunboT">
              <template>
                <el-carousel indicator-position="outside">
                  <el-carousel-item v-for="T in lunboT" :key="T.url">
                    <el-image 
                    :src="T.iurl"
                    ></el-image>
                  </el-carousel-item>
                </el-carousel>
              </template>
            </el-card>
            </el-col>
          <el-col :span="10">
            <el-card style="height:368px;">
                <el-button type="danger" style="margin-bottom:15px;" round>板块目录</el-button><br/>
                <!-- 这里循环五个就换一行(调整过大小之后自动换行了) -->
                <el-button type="primary" round style="margin:10px;" v-for="(plate,index) in plist" :key="index">{{plate.pname}}</el-button>
            </el-card>
            </el-col>
        </el-row>
        
        
        <!-- </div> -->
        <!-- 轮播图 end -->
        <!-- 板块选择 start -->
        <div class="login">
          <el-card class="box-card">
            <!-- 横向搜索 -->
            <el-form :inline="true" :model="articleVo" class="demo-form-inline">
              <el-form-item label="帖子标题">
                <el-input v-model="articleVo.atitle" placeholder="帖子标题"></el-input>
              </el-form-item>
              <el-form-item label="作者姓名">
                <el-input v-model="articleVo.nickname" placeholder="作者姓名"></el-input>
              </el-form-item>
              <el-form-item label="所属板块">
                <el-select v-model="articleVo.pid" placeholder="请选择所属板块">
                  <el-option :label="p.pname" :value="p.pid" v-for="(p,index) in plist" :key="index"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="pageFind()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
        <!-- 板块选择 end -->
        <!-- 帖子内容 start -->
        <template>
          <el-table
            :data="pageinfo.list"
            stripe
            style="width: 100%">
            <el-table-column
              prop="atitle"
              label="帖子名称">
            </el-table-column>
            <el-table-column
              prop="acontent"
              label="帖子内容">
            </el-table-column>
            <el-table-column
              prop="user.nickname"
              label="作者名称">
            </el-table-column>
            <el-table-column
              prop="releaseTime"
              label="发帖时间">
            </el-table-column>
            <el-table-column
              prop="readerNum"
              label="阅读数">
              </el-table-column>
            <el-table-column
              prop="collectNum"
              label="收藏数">
              </el-table-column>
            <el-table-column
              prop="replyNum"
              label="回复数">
              </el-table-column>
            <el-table-column
              prop="state"
              label="帖子状态">
            </el-table-column>
            <el-table-column
              prop="original"
              label="是否原创">
            </el-table-column>
            <el-table-column
              label="删除">
              <template slot-scope="scope">
                <el-button 
                  type="danger" icon="el-icon-delete" circle
                  @click="deleteOne(scope.row.aid)"></el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页助手 start -->
          <div class="block">
              <el-button type="danger" @click="deleteAll()">批量删除</el-button>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="articleVo.pageNum"
                :page-sizes="[4, 6, 8, 10]"
                :page-size="articleVo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageinfo.total">
              </el-pagination>
            </div>
          <!-- 分页助手 end -->
        </template>
        <!-- 帖子内容 end -->
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { findBySelectArticleRyan,editArticleRyan,delAllArticleRyan,findOneArticleRyan,findAllPlateRyan,findYourSelfRyan } from '../../api.js'
export default {
name:'forum',
data () {
    return {
        lunboT : [
          {iurl : require('../../assets/image/mainT/T1.jpg'),name :'',url : "1"},
          {iurl : require('../../assets/image/mainT/T2.jpg'),name :'',url : "2"},
          {iurl : require('../../assets/image/mainT/T3.jpg'),name :'',url : "3"},
          {iurl : require('../../assets/image/mainT/T4.jpg'),name :'',url : "4"},
        ],
        articleVo : {
        pageNum : 1,
        pageSize : 4,
        atitle : '',
        pid : '',
        nickname : '',
        },
        pageinfo : [],
        plist : [],
        user : {}
    }
},
created() {
    this.pageFind()
    this.findAllPlate()
    this.findUser()
},
methods: {
    async findUser(){
      let { data } = await findYourSelfRyan()
      if( data.code == 1 ){
        this.user = data.data
        // 这里要改
        this.user.userImage = require('../../static/1239121522933764096.jpg')
        console.log(this.user.userImage)
      }
    },
    myself(){
      this.$router.push('/main')
    },
    logout(){
      sessionStorage.setItem('token','')
      this.$router.push('/login')
    },
    //查找所有的板块
    async findAllPlate(){
      let { data:p } = await findAllPlateRyan()
      if( p.code == 1 ){
        this.plist = p.data
      }else{
        this.openCenter(p.message,'error')
      }
    },
    async pageFind(){
      let { data:p } = await findBySelectArticleRyan(this.articleVo)
      if( p.code == 1 ){
        this.pageinfo = p.data
      }else{
        this.openCenter(p.message,'error')
      } 
    },
    handleClose(done) {
          done();
    },
    handleSizeChange(val) {
    this.articleVo.pageNum = 1
    this.articleVo.pageSize = val
    this.pageFind()
    },
    handleCurrentChange(val) {
      this.articleVo.pageNum = val
      this.pageFind()
    },
    //设置一个提示窗口
    openCenter(msg,tp){
      this.$message({
        message : msg,
        center : true,
        offset : 120,
        type : tp
      })
    },
},
}
</script>

<style>
.el-header {
  background-color: rgba(25, 0, 255, 0.705);
  color: rgb(51, 51, 51);
  text-align: center;
  line-height: 60px;
  width: 100%;
}
.el-main {
  text-align: center;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
.lunboT{
  padding: 0px;
  width: 810px;
}
.center {
  display: flex;                        /* 布局格式????? 中间的表单居中了!!! */
  align-items: center;                  /* 页面横向居中方法 */
  justify-content: center;              /* 页面竖向居中!!! */
}
.el-card{
  margin: 15px;
}
.syLogo{
  font-size: 30px;
  color: rgb(67, 255, 20);
  float: left;
}
.topMy{
  float: right;
  margin: 10px;
  
}
</style>