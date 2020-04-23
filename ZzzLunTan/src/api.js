// 导入axios
import axios from 'axios'
import { Message } from 'element-ui'

// axios基本配置项
axios.defaults.baseURL='http://localhost:10010/api'
// axios.defaults.timeout=3000

//全局 axios 错误处理
axios.interceptors.response.use(response => {
    return response;
},error => {
    Message.error(error.message)
    return Promise.reject(error);
});

// 配置拦截器
axios.interceptors.request.use(request=>{
    let token = sessionStorage.getItem('token')
    if(token){
        request.headers.authorization = token
    }
    return request
},error=>{})
axios.interceptors.response.use(response => {
    // 放行
    return response
}, error => {
    // 错误提示
    console.info(error)
    Message.error(error.message)
    return Promise.reject(error)
})


//发送请求
export function test (){
    return axios.get('')
}
//登录
export function login (user){
    return axios.post('/userservice/user/login',user)
}
//注册
export function register (user){
    return axios.post('/userservice/user/register',user)
}
//激活用户
export function registerAllow (emailKey){
    return axios.get('/userservice/user/registerAllow/'+emailKey)
}
//添加板块
export function addPlateRyan (plate){
    return axios.post('/plateservice/plate/addPlateRyan',plate)
}
//分页查询
export function findBySelectPlateRyan (plateVo){
    return axios.post('/plateservice/plate/findBySelectPlateRyan',plateVo)
}
//查询单个板块
export function findOnePlateRyan (pid){
    return axios.get('/plateservice/plate/findOnePlateRyan/'+pid)
}
//删除板块
export function delAllPlateRyan (pidList){
    return axios.delete('/plateservice/plate/delAllPlateRyan/'+pidList)
}
//删除板块
export function editPlateRyan (plate){
    return axios.put('/plateservice/plate/editPlateRyan',plate)
}
//查询所有板块
export function findAllPlateRyan (){
    return axios.get('/plateservice/plate/findAllPlateRyan')
}






//用户自己更新用户信息
export function updateUserRyan (user){
    return axios.put('/userservice/user/updateUserRyan',user)
}
//查询当前用户
export function findYourSelfRyan (){
    return axios.get('/userservice/user/findYourSelfRyan')
}
//查询当前用户
export function GetImageRyan (file){
    return axios.post('/userservice/user/GetImageRyan',file)
}






//分页查询所有用户
export function findBySelectUserRyan (userVo){
    return axios.post('/userservice/user/findBySelectUserRyan',userVo)
}
//删除用户
export function delAllUserRyan (uidList){
    return axios.delete('/userservice/user/delAllUserRyan/'+uidList)
}






//添加帖子
export function addArticleRyan (article){
    return axios.post('/articleservice/article/addArticleRyan',article)
}
//分页查询帖子
export function findBySelectArticleRyan (articleVo){
    return axios.post('/articleservice/article/findBySelectArticleRyan',articleVo)
}
//编辑帖子
export function editArticleRyan (article){
    return axios.put('/articleservice/article/editArticleRyan',article)
}
//删除帖子
export function delAllArticleRyan (aidList){
    return axios.delete('/articleservice/article/delAllArticleRyan/'+aidList)
}
//查询单个帖子
export function findOneArticleRyan (aid){
    return axios.get('/articleservice/article/findOneArticleRyan/'+aid)
}




