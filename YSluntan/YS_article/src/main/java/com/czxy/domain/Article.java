package com.czxy.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;            //主贴ID
    private Integer pid;            //所属板块
    private Integer uid;            //所属用户
    private String atitle;          //帖子标题
    private String acontent;        //帖子内容
    @Column(name = "releaseTime")
    private Date releaseTime;       //发帖时间
    @Column(name = "readerNum")
    private Long readerNum;         //阅读数
    @Column(name = "collectNum")
    private Long collectNum;        //收藏数
    @Column(name = "replyNum")
    private Long replyNum;          //回复数
    private Integer state;          //帖子状态
    private Integer original;       //是否原创
    private User user;              //存放作者信息
}
