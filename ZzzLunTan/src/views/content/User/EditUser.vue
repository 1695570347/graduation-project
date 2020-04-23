<template>
  <div>
    <!-- 卡片样式start -->
    <div class="card">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
      <span style="font-size: 40px;">用户管理</span>
      </div>
      <!-- 表单start -->
      <el-form ref="form" :model="userVo" label-width="120px" :inline="true" class="form">
        <el-form-item label="昵称搜索">
          <el-input v-model="userVo.nickname" placeholder="用户昵称 "></el-input>
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
              prop="userImage"
              label="用户头像">
            </el-table-column>
            <el-table-column
              prop="nickname"
              label="用户昵称">
            </el-table-column>
            <el-table-column
              prop="sex"
              label="用户性别">
            </el-table-column>
            <el-table-column
              prop="birthday"
              label="用户生日">
            </el-table-column>
            <el-table-column
              prop="shehuiType"
              label="社会类型">
              </el-table-column>
            <el-table-column
              prop="userRemark"
              label="用户备注">
              </el-table-column>
            <el-table-column
              prop="registerTime"
              label="注册日期">
            </el-table-column>
            <el-table-column
              prop="leader"
              label="是否为版主">
            </el-table-column>
            <el-table-column
              label="删除">
              <template slot-scope="scope">
                <el-button 
                  type="danger" icon="el-icon-delete" circle
                  @click="deleteOne(scope.row.uid)"></el-button>
              </template>
            </el-table-column>
          </el-table>
      <!-- 分页助手 start -->
       <div class="block">
          <el-button type="danger" @click="deleteAll()">批量删除</el-button>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="userVo.pageNum"
            :page-sizes="[4, 6, 8, 10]"
            :page-size="userVo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageinfo.total">
          </el-pagination>
        </div>
      <!-- 分页助手 end -->
        </template>
      <!-- 表格 end -->
    </el-card>
    </div>
    <!-- 卡片样式end -->
  </div>
</template>

<script>
import { delAllUserRyan,findBySelectUserRyan } from '../../../api.js'
export default {
data() {
  return {
    multipleSelection : '',
    dialogVisible: false,
    userVo : {
      pageNum : 1,
      pageSize : 4,
      nickname : '',
    },
    pageinfo : [],
  }
},
created() {
  this.pageFind()
},
methods: {
  async deleteOne(uid){
    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
            let { data:baseResult } = await delAllUserRyan(uid)
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
            let { data:baseResult } = await delAllUserRyan(this.multipleSelection)
            this.$message.success(baseResult.message)
            this.pageFind()
        })
  },
  async pageFind(){
      let { data:p } = await findBySelectUserRyan(this.userVo)
      if( p.code == 1 ){
        this.pageinfo = p.data
      }else{
        this.openCenter(p.message,'error')
      } 
  },
  handleSelectionChange(val) {
    this.multipleSelection = val.map(user => user.uid).join(',');
    console.log(this.multipleSelection)
  },
  handleClose(done) {
        done();
  },
  handleSizeChange(val) {
    this.userVo.pageNum = 1
    this.userVo.pageSize = val
    this.pageFind()
  },
  handleCurrentChange(val) {
    this.userVo.pageNum = val
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