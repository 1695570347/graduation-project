<template>
  <div>
    <!-- 卡片样式start -->
    <div class="card">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
      <span style="font-size: 40px;">板块编辑</span>
      </div>
      <!-- 表单start -->
      <el-form ref="form" :model="plateVo" label-width="120px" :inline="true" class="form">
        <el-form-item label="板块名称搜索">
          <el-input v-model="plateVo.pname" placeholder="板块名称 "></el-input>
        </el-form-item>
        <el-form-item label="版主名称搜索">
          <el-input v-model="plateVo.nickname" placeholder="版主名称搜索 "></el-input>
        </el-form-item>
        <el-form-item label="板块主题搜索">
          <el-input v-model="plateVo.ptitle" placeholder="板块主题 " class="buttons"></el-input>
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
              prop="pname"
              label="板块名称">
            </el-table-column>
            <el-table-column
              prop="user.userImage"
              label="版主头像">
            </el-table-column>
            <el-table-column
              prop="user.nickname"
              label="版主昵称">
            </el-table-column>
            <el-table-column
              prop="ptitle"
              label="板块主题">
              </el-table-column>
            <el-table-column
              prop="pcontent"
              label="板块内容">
              </el-table-column>
            <el-table-column
              prop="clicks"
              label="点击数">
              </el-table-column>
            <el-table-column
              prop="articles"
              label="发帖数">
            </el-table-column>
            <el-table-column
              label="编辑">
              <template slot-scope="scope">
                <el-button
                  type="primary" icon="el-icon-edit" circle
                  @click="beforeEdit(scope.row.pid)"></el-button>
              </template>
            </el-table-column>
            <el-table-column
              label="删除">
              <template slot-scope="scope">
                <el-button 
                  type="danger" icon="el-icon-delete" circle
                  @click="deleteOne(scope.row.pid)"></el-button>
              </template>
            </el-table-column>
          </el-table>
      <!-- 分页助手 start -->
       <div class="block">
          <el-button type="danger" @click="deleteAll()">批量删除</el-button>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="plateVo.pageNum"
            :page-sizes="[4, 6, 8, 10]"
            :page-size="plateVo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageinfo.total">
          </el-pagination>
        </div>
      <!-- 分页助手 end -->
        </template>
      <!-- 表格 end -->
      <!-- 对话框 start -->
        <el-dialog
        title="编辑页面"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        
        <el-form status-icon class="demo-ruleForm" ref="plate" :model="plate" label-width="120px">
          <!-- prop是用来绑定数据的 -->
          <el-form-item label="板块名称">
            <el-input v-model="plate.pname" placeholder="板块名称"></el-input>
          </el-form-item>
          <el-form-item label="版主头像">
            <el-input v-model="plate.user.userImage" placeholder="版主头像"></el-input>
          </el-form-item>
          <el-form-item label="版主名称">
            <el-input v-model="plate.user.nickname" placeholder="版主名称"></el-input>
          </el-form-item>
          <el-form-item label="板块主题">
            <el-input v-model="plate.ptitle" placeholder="板块主题"></el-input>
          </el-form-item>
          <el-form-item label="板块内容">
            <el-input type="textarea" v-model="plate.pcontent"></el-input>
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
    </el-card>
    </div>
    <!-- 卡片样式end -->
  </div>
</template>

<script>
import { findBySelectPlateRyan,findOnePlateRyan,delAllPlateRyan,editPlateRyan } from '../../../api.js'
export default {
data() {
  return {
    multipleSelection : '',
    dialogVisible: false,
    plateVo : {
      pageNum : 1,
      pageSize : 4,
      nickname : '',
      pname : '',
      ptitle : '',
    },
    pageinfo : [],
    plate : {
      pname : '',
      ptitle : '',
      pcontent : '',
      user : {
        userImage :'',
        nickname : '',
      }
    }
  }
},
created() {
  this.pageFind()
},
methods: {
  async deleteOne(pid){
    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
            let { data:baseResult } = await delAllPlateRyan(pid)
            this.$message.success(baseResult.message)
            this.pageFind()
        })
  },
  async deleteAll(pid){
    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
            let { data:baseResult } = await delAllPlateRyan(this.multipleSelection)
            this.$message.success(baseResult.message)
            this.pageFind()
        })
  },
  async pageFind(){
      let { data:p } = await findBySelectPlateRyan(this.plateVo)
      if( p.code == 1 ){
        this.pageinfo = p.data
      }else{
        this.openCenter(p.message,'error')
      } 
  },
  handleSelectionChange(val) {
    this.multipleSelection = val.map(plate => plate.pid).join(',');
    console.log(this.multipleSelection)
  },
  handleClose(done) {
        done();
  },
  async beforeEdit(pid){
    let { data:plate } = await findOnePlateRyan(pid)
    if( plate.code == 1 ){
      //保存数据
      this.plate = plate.data
      //打开对话框
      this.dialogVisible = true
    }else{
      //错误提示
      this.openCenter(plate.message,'error')
    }
  },
  async edit(){
    let { data:edit } = await editPlateRyan(this.plate)
    if( edit.code == 1 ){
      this.openCenter(edit.message,'success')
      this.dialogVisible = false
      this.pageFind()
    }else{
      this.openCenter(edit.message,'error')
    }
  },
  handleSizeChange(val) {
    this.plateVo.pageNum = 1
    this.plateVo.pageSize = val
    this.pageFind()
  },
  handleCurrentChange(val) {
    this.plateVo.pageNum = val
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