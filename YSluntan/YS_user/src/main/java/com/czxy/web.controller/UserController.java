package com.czxy.web.controller;

import com.czxy.domain.User;
import com.czxy.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.IdWorker;
import utils.JwtUtils;
import utils.RasUtils;
import vo.BaseResult;
import vo.UserVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    //生成唯一ID的方法
    @Resource
    private IdWorker idWorker;

    //保存图片的路径
    public static final String ImagePath = "D:\\dev\\workspace\\VueStudy\\ZzzLunTan\\src\\static";

    @Resource
    private UserService userServiceImpl;
    /**
     *用户解密代码地址
     */
    private static final String priKeyPath = "D:\\temp\\password.pri";
    /**
     *登陆
     */
    @PostMapping("/login")
    public BaseResult login(@RequestBody User user){
        User u = userServiceImpl.login(user);
        if (u != null){
            //登录成功
            String token = null;
            try {
                token = JwtUtils.generateToken(u,300, RasUtils.getPrivateKey(priKeyPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return BaseResult.ok("登录成功").append("token",token);
        }else{
            return BaseResult.error("用户名或密码错误");
        }
    }
    /**
     *注册
     */
    @PostMapping("/register")
    public BaseResult register(@RequestBody User user){
        if (user.getUsername().equals("") || user.getEmail().equals("")){
            return BaseResult.error("数据填写不完整");
        }
        if(!user.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")){
            return BaseResult.error("邮箱格式错误");
        }
        try {
            userServiceImpl.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.ok("注册成功");
    }

    /**
     * 激活用户
     */
    @GetMapping("/registerAllow/{emailKey}")
    public BaseResult emailBeUse(@PathVariable("emailKey") String emailKey){
        //查找数据库查看是否能匹配到拥有该游戏的用户
        try {
            userServiceImpl.registerAllow(emailKey);
        } catch (Exception e) {
            //抛出异常就删除旧的用户数据
            userServiceImpl.deleteUser(emailKey);
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.ok("激活成功,可以登录");
    }
    /**
     * 查询单个用户信息
     */
    @GetMapping("/selectOneUserByUid/{uid}")
    public BaseResult selectOneUserByUid(@PathVariable("uid") Integer uid){
        User user = null;
        try {
            user = userServiceImpl.selectOneUserByUid(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("没有这个用户");
        }
        return BaseResult.ok("有这个用户",user);
    }
    /**
     * 查询单个用户信息
     */
    @GetMapping("/selectOneUserByNickname/{nickname}")
    public BaseResult<User> selectOneUserByNickname(@PathVariable("nickname") String nickname){
        User user = null;
        user = userServiceImpl.selectOneUserByNickname(nickname);
        if (user == null){
            return BaseResult.error("没有这个用户");
        }else{
            return BaseResult.ok("有这个用户",user);
        }
    }
    /**
     * 查找当前用户
     */
    @GetMapping("/findYourSelfRyan")
    public BaseResult findYourSelfRyan(HttpServletRequest request){
        String uid = request.getHeader("uid");
        User user = null;
        try {
            user = userServiceImpl.findYourSelfRyan(Integer.parseInt(uid));
        } catch (NumberFormatException e) {
            return BaseResult.error("查找当前用户失败");
        }
        return BaseResult.ok("查找当前用户成功",user);
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/updateUserRyan")
    public BaseResult updateUserRyan(@RequestBody User user, HttpServletRequest request){
        String uid = request.getHeader("uid");
        user.setUid(Integer.parseInt(uid));
        try {
            userServiceImpl.updateUserRyan(user);
        } catch (Exception e) {
            return BaseResult.error("用户信息修改失败");
        }
        return BaseResult.ok("用户信息修改成功");
    }
    /**
     * 分页查询板块
     */
    @PostMapping("/findBySelectUserRyan")
    public BaseResult findBySelectUserRyan(@RequestBody UserVo userVo){
        //创建分页对象
        PageInfo<User> pi = null;
        try {
            pi = userServiceImpl.findBySelectPlate(userVo);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("分页查询失败");
        }
        return BaseResult.ok("分页查询成功",pi);
    }
    /**
     * 删除板块
     */
    @DeleteMapping("/delAllUserRyan/{uidList}")
    public BaseResult delAllUserRyan(@PathVariable("uidList") String uidList){
        //接收到的是一个集合字符串,切割","即可
        String[] split = uidList.split(",");
        ArrayList<Integer> uList = new ArrayList<>();
        for (String s : split) {
            uList.add(Integer.parseInt(s));
        }
        try {
            userServiceImpl.delAllPlate(uList);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("删除失败");
        }
        return BaseResult.ok("删除成功");
    }
    /**
     * 上传头像进行保存
     */
    @PostMapping("/GetImageRyan")
    public BaseResult GetImageRyan(@RequestParam("file")MultipartFile file){
        //设置图片名以及后缀,保证图片不唯一
        String path = idWorker.nextId()+".jpg";
        //放存放图片的路径
        File f = new File(ImagePath+"\\"+path);
        try {
            //传送文件至某个地方f就是那个地方
            file.transferTo(f);//IO异常
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.error("图片上传失败");
        }
        return BaseResult.ok("图片上传成功",path);
    }

}
