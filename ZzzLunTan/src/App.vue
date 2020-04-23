<template>
  <router-view></router-view>
</template>
<script>
export default {
  name : 'app',
  created () {                                      //页面钩子函数:页面加载完成后触发
    setTimeout(() => {                              //定时器,created执行一秒后触发
      window.L2Dwidget.init({
        pluginRootPath: 'live2dw/',                 //指向你的目录
        pluginJsPath: 'lib/',                       //指向你的目录
        pluginModelPath: 'live2d-widget-model-nico/assets/',                                   //中间这个koharu就是你的老婆,想换个老婆,换这个就可以了
        tagMode: false,
        debug: false,
        model: { jsonPath: '/live2dw/live2d-widget-model-koharu/assets/koharu.model.json' },       //中间这个koharu就是你的老婆,想换个老婆,换这个就可以了
        display: { position: 'right', width: 150, height: 300 },                               //调整大小,和位置
        mobile: { show: true },                                                                //要不要盯着你的鼠标看
        log: false  
      })
    }, 300)                                         //页面加载完成之后多久触发(好像是这个)
  }
}
</script>
<style>
</style>
