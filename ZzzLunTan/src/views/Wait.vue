<template>
  <div class="TZ">
    <div>
      <router-link v-show="show" :to="ht">
      <font size=6 color=green >
        {{name}}
      </font>
      </router-link>
      </div>
  </div>
</template>

<script>
import {registerAllow} from'../api.js'
export default {
  data () {
    return {
      show : 'false',
      name : '',
      ht : ''
    }
  },
  async created() {
    //获取浏览器中的参数
    let emailKey = this.$route.query.emailKey     //可以获取到数据
    
    let {data} = await registerAllow(emailKey)
    if(data.code == 1){
      //提示激活成功
      this.$message.success(data.message)
      this.show = true
      this.name = '点击跳转到登录页面'
      this.ht = '/login'
    }else{
      //提示激活失败
      this.$message.error(data.message)
      this.name = '点击跳转到注册页面'
      this.ht = '/register'
    }
  },
  methods: {
    
  },
}
</script>

<style>
.TZ{
  height: 600px;
  display: flex;                        /* 布局格式????? 中间的表单居中了!!! */
  align-items: center;                  /* 页面横向居中方法 */
  justify-content: center;              /* 页面竖向居中!!! */
}
</style>