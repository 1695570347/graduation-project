package com.czxy.service.impl;

import com.czxy.dao.ArticleDao;
import com.czxy.domain.Article;
import com.czxy.domain.User;
import com.czxy.feign.UserFeign;
import com.czxy.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vo.ArticleVo;
import vo.BaseResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Resource
    private UserFeign userFeign;

    @Override
    public void addArticle(Article article) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        article.setReleaseTime();
        articleDao.insert(article);
    }

    @Override
    public PageInfo<Article> findBySelectArticle(ArticleVo articleVo) {
        PageHelper.startPage(articleVo.getPageNum(),articleVo.getPageSize());
        articleVo.setNickname("%"+articleVo.getNickname()+"%");
        articleVo.setAtitle("%"+articleVo.getAtitle()+"%");
        List<Article> alist = articleDao.findBySelectArticle(articleVo);
        //遍历添加作者信息
        for (Article article : alist) {
            BaseResult<User> user = userFeign.selectOneUser(article.getUid());
            article.setUser(user.getData());
        }
        PageInfo<Article> pi = new PageInfo<>(alist);
        return pi;
    }

    @Override
    public void editArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delAllArticle(ArrayList<Integer> aList) {
        for (Integer aid : aList) {
            articleDao.deleteByPrimaryKey(aid);
        }
    }

    @Override
    public Article findOneArticle(String aid) {
        return articleDao.selectByPrimaryKey(aid);
    }

    @Override
    public void delByUidRyan(Integer uid) {
        articleDao.deleteByUid(uid);
    }
}
