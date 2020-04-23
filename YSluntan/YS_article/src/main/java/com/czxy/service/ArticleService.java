package com.czxy.service;

import com.czxy.domain.Article;
import com.github.pagehelper.PageInfo;
import vo.ArticleVo;

import java.util.ArrayList;

public interface ArticleService {

    void addArticle(Article article);

    PageInfo<Article> findBySelectArticle(ArticleVo articleVo);

    void editArticle(Article article);

    void delAllArticle(ArrayList<Integer> aList);

    Article findOneArticle(String aid);

    void delByUidRyan(Integer uid);

}
