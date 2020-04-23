package com.czxy.domain;

import lombok.Data;

import java.util.Date;
@Data
public class User {
    private Integer uid;            //用户id
    private String nickname;        //昵称
    private String username;        //账号
    private String password;        //密码
    private String email;           //邮箱
    private Date birthday;          //生日
    private String sex;             //性别
    private String shehuiType;      //社会类型
    private String userImage;       //用户头像
    private Integer grade;          //等级
    private String userRemark;      //用户备注
    private Date registerTime;      //注册日期
    private Integer userIntegral;   //用户积分
    private Integer state;          //登录状态
    private Integer leader;         //是否为版主
    private String intro;           //简介
}
