<template>
  <div>
    <!-- 卡片样式start -->
    <div class="card">
    <el-card class="box-card">
       <div slot="header" class="clearfix">
        <span style="font-size: 40px;">帖子发布</span>
       </div>  
       <!-- 表单start -->
       <div class="myform">
        <el-form status-icon class="demo-ruleForm" :rules="rules" ref="plate" :model="article" label-width="120px">
          <!-- prop是用来绑定数据的 -->
          <el-form-item label="帖子主题" prop="atitle">
            <el-input v-model="article.atitle" placeholder="帖子主题" autocomplete="off"></el-input>
          </el-form-item>
          <!-- <el-form-item label="所属板块" prop="ptitle">
            <el-input v-model="article.ptitle" placeholder="所属板块"></el-input>
          </el-form-item> -->
          <el-form-item label="所属板块">
            <el-select v-model="article.pid" placeholder="请选择所属板块">
              <el-option :label="p.pname" :value="p.pid" v-for="(p,index) in plist" :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="帖子内容" prop="acontent">
            <el-input type="textarea" v-model="article.acontent"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addArticle()">添加</el-button> 
            <el-button @click="resetForm('plate')">重置</el-button>
          </el-form-item>
        </el-form>
        </div>
        <!-- 表单end -->
      <div>
      </div>
    </el-card>
    </div>
    <!-- 卡片样式end -->
  </div>
</template>

<script>
import { addArticleRyan,findAllPlateRyan } from '../../../api.js'
// import { Form } from 'element-ui';
export default {
data () {
  // 1.在这里编写判断数据
  var validateatitle = (rule, value, callback) => {
    if (!value) {
      //这里显示填写完之后的错误格式
      return callback(new Error('帖子必须要有题目'))
    }else{
      //这里显示填写完之后的正确格式
      callback();
    }
  };
  var validateacontent = (rule, value, callback) => {
    if (!value) {
      //这里显示填写完之后的错误格式
      return callback(new Error('帖子内容不能为空'))
    }else{
      //这里显示填写完之后的正确格式
      callback();
    }
  };

  return {
    plist : [],
    article : {
      atitle : '',
      pid : '',
      acontent : '',
    },
    rules: {
      // 2.下面对象的第一个值是  1 中的判断数据,第二个值是显示方式
      atitle: [
        { validator: validateatitle, trigger: 'blur' }
      ],
      acontent: [
        { validator: validateacontent, trigger: 'blur' }
      ]
    }
  }
},

  created() {
    this.findAllPlate()
  },

  methods: {
    // 这里是添加方法
    async addArticle(){
      let {data:add} = await addArticleRyan(this.article)
      if(add.code == 1){
        this.openCenter(add.message,'success')
        this.$router.push('/editarticle')
      }else{
        this.openCenter(add.message,'error')
      }
    },
    // 这里是重置方法
    resetForm(plate){
        this.$refs[plate].resetFields();
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
.myform{
  display: flex;                        /* 布局格式????? 中间的表单居中了!!! */
  justify-content: center;              /* 页面竖向居中!!! */
}
.myform input{
  width: 500px;
}
</style>