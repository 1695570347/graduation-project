package com.czxy.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;            //用户id
    private String nickname;        //昵称
    private String username;        //账号
    @Column(name = "PASSWORD")
    private String password;        //密码
    private String email;           //邮箱
    @Column(name = "emailKey")
    private String emailKey;        //生成的随机激活key
    @Column(name = "emailKeyTime")
    private Long emailKeyTime;   //邮箱激活的有效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;          //生日
    private String sex;             //性别
    @Column(name = "shehuiType")
    private String shehuiType;      //社会类型
    @Column(name = "userImage")
    private String userImage;       //用户头像
    private Integer grade;          //等级
    @Column(name = "userRemark")
    private String userRemark;      //用户备注
    @Column(name = "registerTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date registerTime;      //注册日期
    @Column(name = "userIntegral")
    private Integer userIntegral;   //用户积分
    private Integer state;          //激活状态
    private Integer leader;         //是否为版主
    private String intro;           //简介
}
