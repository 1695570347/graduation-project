<template>
<!-- 背景start -->
  <div id="building">
    <div style="margin:45px 60px;">
    <!-- 图片标签start -->
      <div class="block" v-for="html in myhtml" :key="html.url">
        <router-link target="_blank" :to="html.url">
        <el-image
          :id="html.url"
          style="width: 150px; height: 90px; border: 3px solid black"
          :src="html.iurl"
          fit="fit"
          @click="open(html.id)">
        </el-image>
        <br>
        <div align="center" class="tishiname">
          <span>{{ html.name }}</span>
        </div>
        </router-link>
      </div>
    <!-- 图片标签end -->
    </div>
     <router-link to="/login">
     <el-button type="success" class="login" v-show="dl">登录</el-button>
     </router-link>
     <router-link to="/lookArticle">
     <el-button type="success" v-show="dl">查看帖子页面</el-button>
     </router-link>
    <!-- 自己的信息 start -->
    <el-form ref="form" :model="user" label-width="80px">
      <el-card class="myself">
      <el-row style="margin-bottom: 30px;">
        <el-col :span="11">
        <!-- 头像区域 start -->
        <!-- <div style="border: 3px solid black;"> -->
          <el-image 
          :src="user.userImage"
          fit='cover' 
          class="userImage"></el-image>
        <!-- </div> -->
        <!-- 头像区域 end -->
        </el-col>
        <el-col :span="13">
          <el-form-item label="等级">
            <el-input v-model="user.grade" readonly style="width:60px;"></el-input>
            ( • ̀ω•́ )✧
          </el-form-item>
          <el-form-item label="用户积分">
            <el-input v-model="user.userIntegral"  readonly style="width:60px;"></el-input>
            ( • ̀ω•́ )✧
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="user.nickname" :disabled=edit></el-input>
          </el-form-item>
          <el-form-item>
            <!-- 上传头像组件 start -->
            <el-upload
              class="avatar-uploader"
              action="http://localhost:10010/api/userservice/user/GetImageRyan"
              :headers="MyHeader"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <el-button type="danger" v-show="!show">上传头像</el-button>
            </el-upload>
            <!-- 上传头像组件 end -->
            <!-- 这里可以放一个是否为版主 -->
          </el-form-item>
        </el-col>
      </el-row>
      
      <!-- 基本信息 start -->
        <el-form-item label="社会类型">
          <el-input v-model="user.shehuiType" :disabled=edit></el-input>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input type="textarea" v-model="user.intro" :disabled=edit></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="beforeupdate()" v-show="show">修改个人资料</el-button>
          <el-button type="danger" @click="updateNo()" v-show="!show">取消</el-button>
          <el-button type="success" @click="updateYes()" v-show="!show">修改</el-button>
          <el-button type="danger" style="float:right;" @click="logout()">注销</el-button>
        </el-form-item>
      <!-- 基本信息 end -->
      </el-card>
    </el-form>
    <!-- 自己的信息 end -->
    <el-dialog
      :visible.sync="dialogVisible"
      width="30%" 
      :modal='false' 
      :modal-append-to-body='false' 
      :before-close="handleClose">
      <span>您还未登录</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="login()">登 录</el-button>
      </span>
    </el-dialog>
  </div>
  <!-- 背景end -->
</template>

<script>
//获取token
var token = sessionStorage.getItem('token')
import { updateUserRyan,findYourSelfRyan } from '../api.js'
export default {
  data() {
    return {
      dl : true,
      MyHeader : { Authorization : token},
      dialogVisible : false,
      show : true,
      edit : true,
      myLJ : '',
      dialogVisible :false,
      src: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
      myhtml : [
        {iurl : require('../assets/image/background/Cartoon.jpg'),name :'动漫',url : "/cartoon"},
        {iurl : require('../assets/image/background/Chat.jpg'),name :'闲聊',url : "/chat"},
        {iurl : require('../assets/image/background/Forum.jpg'),name :'论坛',url : "/forum"},
        {iurl : require('../assets/image/background/gly.jpg'),name :'管理员',url : "/gly"},
      ],
      user : {
        nickname : '还没有给自己起名呢!',
        birthday : '****/**/**',
        shehuiType : '社畜',
        userImage : require('../assets/image/background/userImage.png'),
        grade : 1,
        userIntegral : '0',
        intro : '',
        leader : '',
      },
    }
  },
  created() {
    this.findYourSelf()
  },
  methods: {
    async findYourSelf(){
      if(sessionStorage.getItem('token') == null){
        this.openCenter('未检测到登陆状态,请登陆','error')
      }else{
      let { data:you } = await findYourSelfRyan(this.user)
      if( you.code == 1 ){
        this.user = you.data
        // console.log(you.data.userImage)
        // this.user.userImage = require(you.data.userImage)
        this.dl = false
        if( this.user.userImage == null ){
          this.user.userImage = require('../assets/image/background/userImage.png')
        }
      }
      }
    },

    // 上传图片 成功的钩子
    handleAvatarSuccess(res, file) {
      //这个res就是我们的返回数据
      // console.log(res.data)
      //图片的路径
      var imgUrl = res.data
      this.user.userImage = '../static/'+res.data
      console.log(this.user.userImage)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    // 上传图片 end
    beforeupdate(){
      if(sessionStorage.getItem('token') == null){
        this.dialogVisible = true
      }else{
        this.edit = !this.edit
        this.show = !this.show
        //保存一波原有数据
        // this.u = this.user
        // console.log(this.u)
      }
    },
    logout(){
      if(sessionStorage.getItem('token') == null){
        this.dialogVisible = true
      }else{
        sessionStorage.setItem('token','')
        this.$router.push('/login')
      }
    },
    updateNo(){
      //取消改变就将user变为原有变量
      // this.user = this.u
      // console.log(this.u)
      this.edit = !this.edit
      this.show = !this.show
    },
    async updateYes(){
      let { data:update } = await updateUserRyan(this.user)
      if( update.code == 1){
        this.openCenter(update.message,'success')
        this.edit = !this.edit
        this.show = !this.show
      }else{
        this.openCenter(update.message,'error')
      }
    },
    handleClose(done) {
      done();
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

<style scoped>
  @import "../css/reset.css";
  /* 背景图片数据 */
  #building{
  background:url("../assets/image/BJ1.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;}
  
  .tishiname{
    width: 150px;
  }
  .tishiname>span{
    font-size: 30px;
    color:darkgoldenrod;
  }
  .block{
    margin-left: 50px;
    padding: 15px;
    width:200px;
    height: 150px;
  }
  .myself{
    background-color: rgb(0, 174, 255);
    position: absolute;
    width: 700px;
    height: 500px;
    left: 60%;
    top: 8%;
    opacity: 1;
  }
  .userImage {
    margin-left: 30px;
    margin-top: 30px;
    width: 240px;
    height: 160px;
    border-radius: 50px;
    border: 3px;
  }
  .login{
    position: absolute;
    left: 40%;
    top: 8%;
    width: 100px;
    height: 100px;
  }
</style>
  