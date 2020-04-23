import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '首页',
    component: Main
  },
  {
    path: '/login',
    name: '登录',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/main',
    name: '首页',
    component: () => import('../views/Main.vue')
  },
  {
    path: '/register',
    name: '注册页面 ',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/cartoon',
    name: '动漫视频 ',
    component: () => import('../views/content/Cartoon.vue')
  },
  {
    path: '/chat',
    name: '聊天室 ',
    component: () => import('../views/content/Chat.vue')
  },
  {
    path: '/forum',
    name: '论坛 ',
    component: () => import('../views/content/Forum.vue')
  },
  {
    path: '/lookArticle',
    name: '查看帖子 ',
    component: () => import('../views/content/Article/LookArticle.vue')
  },
  {
    path: '/gly',
    name: '板块 ',
    component: () => import('../views/content/gly.vue'),
    redirect: 'addplate',
    children : [
      {
        path: '/addplate',
        name: '添加板块 ',
        component: () => import('../views/content/Plate/AddPlate.vue')
      },
      {
        path: '/editplate',
        name: '编辑板块 ',
        component: () => import('../views/content/Plate/EditPlate.vue')
      },
      {
        path: '/addArticle',
        name: '添加帖子 ',
        component: () => import('../views/content/Article/AddArticle.vue')
      },
      {
        path: '/editArticle',
        name: '编辑帖子 ',
        component: () => import('../views/content/Article/EditArticle.vue')
      },
      {
        path: '/editUser',
        name: '编辑用户 ',
        component: () => import('../views/content/User/EditUser.vue')
      },
    ]
  },
  {
    path: '/wait',
    name: '等待页面 ',
    component: () => import('../views/Wait.vue')
  },
  // {
  //   path: '/test',
  //   name: '测试',
  //   component: () => import('../views/Test.vue')
  // },
  // {
  //   path: '/main',
  //   name: '首页',
  //   component: () => import('../views/Main.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
