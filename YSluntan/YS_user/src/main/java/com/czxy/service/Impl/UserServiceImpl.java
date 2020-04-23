package com.czxy.service.Impl;

import com.czxy.dao.UserDao;
import com.czxy.domain.User;
import com.czxy.service.UserService;
import com.czxy.utils.ArticleFeign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import utils.EmailUtils;
import utils.IdWorker;
import utils.MailUtil;
import vo.UserVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleFeign articleFeign;

    @Resource
    private IdWorker idWorker;
    /**
     * 登录
     */
    @Override
    public User login(User user) {
        return userDao.login(user);
    }
    /**
     * 注册
     */
    @Override
    public void register(User user) {
        //查找数据库,观察是否有相同的用户数据
        User u = userDao.selectOne(user);
        if (u != null){
            //找到相同用户名,提示已注册.
            throw new RuntimeException("用户名已注册");
        }
        //未注册用户需要生成邮箱激活码进行邮箱验证
        String code = EmailUtils.getCode();
        //保存用户到数据库,显示的是未激活用户
        user.setState(0);
        //存入激活码
        user.setEmailKey(code);
        //存入激活码有效时间往后十分钟
        user.setEmailKeyTime(EmailUtils.getKryEndTime());
        try {
            user.setGrade(1);
            user.setIntro("这个人啥也没写");
            user.setLeader(0);
            user.setUserIntegral(0);
            //储存用户
            userDao.insert(user);
        } catch (Exception e) {
            //储存用户失败
            e.printStackTrace();
        }
        //保存用户之后通过线程的方式给用户发送一封邮件
        new Thread(new MailUtil(user.getEmail(),user.getEmailKey())).start();
    }

    @Override
    public void registerAllow(String emailKey) {
        //查询所有用户观察是否有匹配信息
        User user = null;
        user = userDao.selectWithEmailKey(emailKey);
        if (user == null){
            throw new RuntimeException("该用户没有注册,不存在激活码");
        }else{
            //找到了用户,还需要鉴别他的激活时间是否超时
            Long emailKeyTime = user.getEmailKeyTime();
            Long newTime = new Date().getTime();
            if ((newTime - emailKeyTime) > (1000*60*10)){
                //超时
                throw new RuntimeException("激活码超时,请重新注册");
            }else{
                //激活成功修改用户状态
                user.setState(1);
                //给予用户默认昵称
                user.setNickname("咸鱼"+idWorker.nextId());
                userDao.updateByPrimaryKey(user);
                //跳转登录页面
            }
        }
    }

    @Override
    public void deleteUser(String emailKey) {
        userDao.deleteByemailKey(emailKey);
    }

    @Override
    public User selectOneUserByUid(Integer uid) {
        return userDao.selectByPrimaryKey(uid);
    }

    @Override
    public User selectOneUserByNickname(String nickname) {
        Example example = new Example(User.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("nickname",nickname);
        User user = userDao.selectOneByExample(example);
        System.out.println(user);
        return user;
    }

    @Override
    public void updateUserRyan(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findYourSelfRyan(int parseInt) {
        return userDao.selectByPrimaryKey(parseInt);
    }

    @Override
    public PageInfo<User> findBySelectPlate(UserVo userVo) {
        PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize());
        userVo.setNickname("%"+userVo.getNickname()+"%");
        List<User> ulist = userDao.findBySelectPlate(userVo);
        PageInfo<User> pi = new PageInfo<>(ulist);
        return pi;
    }

    @Override
    public void delAllPlate(ArrayList<Integer> uList) {
        for (Integer uid : uList) {
            userDao.deleteByPrimaryKey(uid);
            //把该用户所有的帖子都删了
            articleFeign.delByUidRyan(uid);
        }
    }


}
