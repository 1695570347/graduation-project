package com.czxy.service;

import com.czxy.domain.User;
import com.github.pagehelper.PageInfo;
import vo.UserVo;

import java.util.ArrayList;

public interface UserService {

    User login(User user);

    void register(User user);

    void registerAllow(String emailKey);

    void deleteUser(String emailKey);

    User selectOneUserByUid(Integer uid);

    User selectOneUserByNickname(String nickname);

    void updateUserRyan(User user);

    User findYourSelfRyan(int parseInt);

    PageInfo<User> findBySelectPlate(UserVo userVo);

    void delAllPlate(ArrayList<Integer> uList);

}
