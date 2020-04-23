<template>
  <div class="homepage-hero-module">
  <div class="video-container">
  <div :style="fixStyle" class="filter">
   <!--内容-->
   <!-- 登录区域 start -->
   <el-form ref="form" :model="user" :rules="rules">
    <div class="login">
      <div class="login-top">
        <span>
          <h1>Login</h1>
        </span>
      </div>
      <div class="login-center">
        <!-- clearfix  清除浮动防止合并 -->
        <div class="login-center clearfix">
        <div class="login-center-img"><img src="../assets/image/username.jpg" alt=""></div>
        <div class="login-center-input">
          <el-form-item prop="username">
            <el-input v-model="user.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <!-- <input type="text" name="" value="" placeholder="请输入您的用户名" v-model="user.username"/> -->
          <!--<div class="login-center-input-text">用户名不能为空</div>-->
        </div>
        </div>
        <div class="login-center clearfix">
        <div class="login-center-img"><img src="../assets/image/password.jpg" alt=""></div>
        <div class="login-center-input">
          <el-form-item prop="password">
            <el-input type="password" v-model="user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <!-- <input type="password"  name="" value="" placeholder="请输入您的密码" v-model="user.password"/> -->
          <!--<div class="login-center-input-text">密码不能为空</div>-->
        </div>
        <!-- 自动登录 以及 记住密码 start -->
        <div>
          <el-form-item style="float:left;padding-top:15px;">
            <font class="login-center-fr">自动登录</font>
            <el-checkbox name="type" v-model="user.autoLogin"></el-checkbox>
            <font class="login-center-fr" style="padding-left:70px;">记住密码</font>
            <el-checkbox name="type" v-model="user.remember"></el-checkbox>
          </el-form-item>
        </div>
        <!-- 自动登录 以及 记住密码 end -->
        </div>
        <div class="login-button" @click="loginFn()">
          登陆
        <hr style="width:240px;">
        </div>
        <!-- 忘记密码 -->
        <div class="login-center-forget">
          <router-link to=""><font class="login-center-fr">忘记密码</font></router-link>
        </div>
        <!-- 注册账号 -->
        <div class="login-center-register">
          <router-link to="/register"><font class="login-center-fr">注册新用户</font></router-link>
        </div>
      </div>
    </div>
   </el-form>
   <!-- 登录区域 end -->
     
  </div>
  <video :style="fixStyle" autoplay loop muted class="fillWidth" v-on:canplay="canplay">
   <source src="../assets/video/G1s.mp4" type="video/mp4"/>
  
   <source src="../assets/video/G1w.webm" type="video/webm"/>

  </video>
  <div class="poster hidden" v-if="!vedioCanPlay">
   <img :style="fixStyle" src="../assets/video/G1.jpg" alt="">
  </div>
  </div>
 </div>
 
</template>

<script>
import { login } from '../api.js'
export default {
 name: 'Video',
 data() {
  return {
    rules : {
        username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 4, max: 16, message: '长度在 4 到 16 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码必填', trigger: 'blur' },
          ]
      },
      user : {
      username : '',
      password : '',
      autoLogin : '',
      remember : ''
      },
      autologin : '',
      remember : '',

      vedioCanPlay: false,
      fixStyle: ''
  }
 },
 methods: {
   //视频播放是否开启,好像是这个???!!!
  canplay() {
  this.vedioCanPlay = true
  },
  //账号密码格式判定
  async loginFn(){
    //非空判定
    if (!this.user.username || !this.user.password) {
      this.openCenter('用户名或密码不能为空','warning')
    }else{
      //发送请求获取结果
      let { data } = await login(this.user)
      if (data.code == 1) {
        console.log(data.message)
        this.openCenter(data.message,'success') 
        //登录成功之后保存token数据
        sessionStorage.setItem('token',data.other.token)
        this.$router.push('/main')
      }else{
        console.log(data.message)
        this.openCenter(data.message,'error') 
      }
    }
    //可注释
    // console.log(this.user)
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

 //挂载时
 mounted: function() { //屏幕自适应
  window.onresize = () => {
  //窗口宽度
  const windowWidth = document.body.clientWidth
  //窗口高度
  const windowHeight = document.body.clientHeight
  const windowAspectRatio = windowHeight / windowWidth
  //视频宽度
  let videoWidth
  //视频高度
  let videoHeight
  if (windowAspectRatio < 0.5625) {
   videoWidth = windowWidth
   videoHeight = videoWidth * 0.5625
   this.fixStyle = {
   height: windowWidth * 0.5625 + 'px',
   width: windowWidth + 'px',
   'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
   'margin-left': 'initial'
   }
  } else {
   videoHeight = windowHeight
   videoWidth = videoHeight / 0.5625
   this.fixStyle = {
   height: windowHeight + 'px',
   width: windowHeight / 0.5625 + 'px',
   'margin-left': (windowWidth - videoWidth) / 2 + 'px',
   'margin-bottom': 'initial'
   }
  }
  }
  window.onresize()
 }
 }
</script>

<style scoped>
@import "../css/reset.css";
@import "../css/style.css";
</style>