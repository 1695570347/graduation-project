<template>
  <div>
    <!-- 卡片样式start -->
    <div class="card">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
      <span style="font-size: 40px;">帖子管理</span>
      </div>
      <!-- 表单start -->
      <el-form ref="form" :model="articleVo" label-width="120px" :inline="true" class="form">
        <el-form-item label="帖名搜索">
          <el-input v-model="articleVo.atitle" placeholder="帖子名称 "></el-input>
        </el-form-item>
        <el-form-item label="帖子作者搜索">
          <el-input v-model="articleVo.nickname" placeholder="作者搜索 "></el-input>
        </el-form-item>
        <el-form-item label="所属板块">
          <el-select v-model="articleVo.pid" placeholder="请选择所属板块">
            <el-option :label="p.pname" :value="p.pid" v-for="(p,index) in plist" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-button type="primary" @click="pageFind()">查询</el-button> 
      </el-form>
      <!-- 表单end -->
      <!-- 表格 start -->
      <template>
          <el-table
            ref="multipleTable"
            :data="pageinfo.list"
            stripe
            style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
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
              label="编辑">
              <template slot-scope="scope">
                <el-button
                  type="primary" icon="el-icon-edit" circle
                  @click="beforeEdit(scope.row.aid)"></el-button>
              </template>
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
      <!-- 对话框 start -->
      <el-dialog
      title="编辑页面"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      
      <el-form status-icon class="demo-ruleForm" ref="plate" :model="article" label-width="120px">
        <!-- prop是用来绑定数据的 -->
        <el-form-item label="帖子题目">
          <el-input v-model="article.atitle" placeholder="板块名称"></el-input>
        </el-form-item>
        <el-form-item label="所属板块">
          <el-select v-model="article.pid" placeholder="请选择所属板块">
            <el-option :label="p.pname" :value="p.pid" v-for="(p,index) in plist" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="帖子内容">
          <el-input type="textarea" v-model="article.acontent"></el-input>
        </el-form-item>
        <el-form-item>
          <template>
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="edit()">编 辑</el-button>
          </template>
        </el-form-item>
      </el-form>
      </el-dialog>
    <!-- 对话框 end -->
      <!-- 表格 end -->
    </el-card>
    </div>
    <!-- 卡片样式end -->
  </div>
</template>

<script>
import { findBySelectArticleRyan,editArticleRyan,delAllArticleRyan,findOneArticleRyan,findAllPlateRyan } from '../../../api.js'
export default {
data() {
  return {
    multipleSelection : '',
    dialogVisible: false,
    articleVo : {
      pageNum : 1,
      pageSize : 4,
      atitle : '',
      pid : '',
      nickname : '',
    },
    pageinfo : [],
    plist : [],
    article : {
      atitle : '',
      pid : '',
      acontent : '',
    },
  }
},
created() {
  this.pageFind()
  this.findAllPlate()
},
methods: {
  //查找所有的板块
  async findAllPlate(){
    let { data:p } = await findAllPlateRyan()
    if( p.code == 1 ){
      this.plist = p.data
    }else{
      this.openCenter(p.message,'error')
    }
  },
  async deleteOne(aid){
    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
            let { data:baseResult } = await delAllArticleRyan(aid)
            this.$message.success(baseResult.message)
            this.pageFind()
        })
  },
  async deleteAll(){
    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
            let { data:baseResult } = await delAllArticleRyan(this.multipleSelection)
            this.$message.success(baseResult.message)
            this.pageFind()
        })
  },
  async pageFind(){
      let { data:p } = await findBySelectArticleRyan(this.articleVo)
      if( p.code == 1 ){
        this.pageinfo = p.data
      }else{
        this.openCenter(p.message,'error')
      } 
  },
  handleSelectionChange(val) {
    this.multipleSelection = val.map(plate => plate.pid).join(',');
  },
  handleClose(done) {
        done();
  },
  async beforeEdit(aid){
    let { data:a } = await findOneArticleRyan(aid)
    if( a.code == 1 ){
      //保存数据
      this.article = a.data
      //打开对话框
      this.dialogVisible = true
    }else{
      //错误提示
      this.openCenter(a.message,'error')
    }
  },
  async edit(){
    let { data:edit } = await editArticleRyan(this.article)
    if( edit.code == 1 ){
      this.openCenter(edit.message,'success')
      this.dialogVisible = false
      this.pageFind()
    }else{
      this.openCenter(edit.message,'error')
    }
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
/* .form{   居中
  display: flex;                        
  justify-content: center;              
} */
</style>