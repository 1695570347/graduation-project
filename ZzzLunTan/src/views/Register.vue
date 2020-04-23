<template>
  <div id="register">
    <div class="card">
      <el-form ref="form" :model="user" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="密码确认" prop="password">
            <el-input v-model="user.passwordx" placeholder="请再次输入密码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="user.email" placeholder="请输入电子邮箱"></el-input>
          </el-form-item>
          <el-form-item label="生日">
             <el-date-picker
                v-model="user.birthday"
                type="date"
                format="yyyy/MM/dd"
                placeholder="选择日期">
             </el-date-picker>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="user.sex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
        <el-form-item>
          <el-button style="width:100px;" type="primary" @click="registerFn()">注册</el-button>
        </el-form-item>
        </el-col>
      </el-row>
      </el-form>
        
        
    </div>
  </div>
</template>

<script>
import { register } from '../api.js'
export default {
data() {
  return {
    rules : {
      username : [
        { required : true ,message : "请输入用户名" ,tirgger : "blur" },
        { min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: 'blur' }
      ],
      password : [
        { required: true, message: '密码必填', trigger: 'blur' },
        { min: 8, message: '密码长度必须超过 8 位', trigger: 'blur' }
      ],
      email : [
        { required: true, message: '邮箱必填', trigger: 'blur' },
      ],
    },
    user : {
      username : '',
      password : '',
      passwordx : '',
      email : '',
      birthday : '',
      sex : '',
    }
  }
},
methods: {
  async registerFn(){
    if (this.user.password == this.user.passwordx) {
      console.log(this.user)
      let { data } = await register(this.user)
      // if (data.code == 1) {
        this.openCenter('邮件已发送,请激活邮箱再登录',"success")
        this.$router.push('/login')
      // }else{
      //  this.openCenter(data.message,"error") 
      // }
      }else{
      this.openCenter("两次输入的密码不一致","warning")
    }
  },
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

<style scoped>
/* 背景图片数据 */
  #register{
  background:url("../assets/image/BJ3.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;}
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .card {
    height: 400px;                        /* 内容高度 */
    width: 650px;                         /* 内容宽度 */
    padding: 30px;                        /* 内边距 上 右 下 左 */
    text-align: center;                   /* 文本居中 */
    position: absolute;                   /* 盒子的放置位置 : 绝对位置 */
    top: 0%;
    left: 15%;
  }
  
</style>