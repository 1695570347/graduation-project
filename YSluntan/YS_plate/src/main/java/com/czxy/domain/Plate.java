package com.czxy.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "plate")
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;            //板块ID
    private String pname;           //板块名称
    private Integer uid;            //用户ID
    private String ptitle;          //板块主题
    private String pcontent;        //板块内容
    private Long clicks;            //点击数
    private Long articles;          //发帖数
    private String nickname;        //版主名称
    private User user;              //附加版主信息
}
