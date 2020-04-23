<template>
  <div>
    <!-- 卡片样式start -->
    <div class="card">
    <el-card class="box-card">
       <div slot="header" class="clearfix">
        <span style="font-size: 40px;">添加板块</span>
       </div>  
       <!-- 表单start -->
       <div class="myform">
        <el-form status-icon class="demo-ruleForm" :rules="rules" ref="plate" :model="plate" label-width="120px">
          <!-- prop是用来绑定数据的 -->
          <el-form-item label="板块名称" prop="pname">
            <el-input v-model="plate.pname" placeholder="板块名称" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="版主名称" prop="nickname">
            <el-input v-model="plate.nickname" placeholder="版主名称"></el-input>
          </el-form-item>
          <el-form-item label="板块主题" prop="ptitle">
            <el-input v-model="plate.ptitle" placeholder="板块主题"></el-input>
          </el-form-item>
          <el-form-item label="板块内容">
            <el-input type="textarea" v-model="plate.pcontent"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addplate()">添加</el-button> 
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
import { addPlateRyan } from '../../../api.js'
// import { Form } from 'element-ui';
export default {
data () {
  // 1.在这里编写判断数据
  var validatepname = (rule, value, callback) => {
    if (!value) {
      //这里显示填写完之后的错误格式
      return callback(new Error('板块名字不能为空'))
    }else{
      //这里显示填写完之后的正确格式
      callback();
    }
  };
  var validatenickname = (rule, value, callback) => {
    if (!value) {
      //这里显示填写完之后的错误格式
      return callback(new Error('板主名字不能为空'))
    }else{
      //这里显示填写完之后的正确格式
      callback();
    }
  };
  var validateptitle = (rule, value, callback) => {
    if (!value) {
      //这里显示填写完之后的错误格式
      return callback(new Error('板块主题不能为空'))
    }else{
      //这里显示填写完之后的正确格式
      callback();
    }
  };

  return {
    plate : {
      pname : '',
      nickname : '',
      ptitle : '',
      pcontent : '',
    },
    rules: {
      // 2.下面对象的第一个值是  1 中的判断数据,第二个值是显示方式
      pname: [
        { validator: validatepname, trigger: 'blur' }
      ],
      nickname: [
        { validator: validatenickname, trigger: 'blur' }
      ],
      ptitle: [
        { validator: validateptitle, trigger: 'blur' }
      ]
    }
  }
},

  methods: {
    // 这里是添加方法
    async addplate(){
      let {data:add} = await addPlateRyan(this.plate)
      if(add.code == 1){
        this.openCenter(add.message,'success')
        this.$router.push('/editplate')
      }else{
        this.openCenter(add.message,'error')
      }
    },
    // 这里是重置方法
    resetForm(plate){
        this.$refs[plate].resetFields();
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