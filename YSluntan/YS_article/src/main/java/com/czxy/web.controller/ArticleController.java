package com.czxy.web.controller;

import com.czxy.domain.Article;
import com.czxy.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import vo.ArticleVo;
import vo.BaseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleServiceImpl;

    /**
     * 添加帖子
     */
    @PostMapping("/addArticleRyan")
    public BaseResult addArticle(@RequestBody Article article, HttpServletRequest request){
        String uid = request.getHeader("uid");
        article.setUid(Integer.parseInt(uid));
        try {
            articleServiceImpl.addArticle(article);
        } catch (Exception e) {
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.ok("添加成功");
    }

    /**
     * 分页查询帖子
     */
    @PostMapping("/findBySelectArticleRyan")
    public BaseResult findBySelectArticle(@RequestBody ArticleVo articleVo){
        //创建分页对象
        PageInfo<Article> pi = null;
        try {
            pi = articleServiceImpl.findBySelectArticle(articleVo);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("分页查询失败");
        }
        return BaseResult.ok("分页查询成功",pi);
    }
    /**
     * 编辑帖子
     */
    @PutMapping("/editArticleRyan")
    public BaseResult editArticle(@RequestBody Article article){
        try {
            articleServiceImpl.editArticle(article);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("编辑失败");
        }
        return BaseResult.ok("编辑成功");
    }
    /**
     * 删除帖子
     */
    @DeleteMapping("/delAllArticleRyan/{aidList}")
    public BaseResult delAllArticle(@PathVariable("aidList") String aidList){
        //接收到的是一个集合字符串,切割","即可
        String[] split = aidList.split(",");
        ArrayList<Integer> aList = new ArrayList<>();
        for (String s : split) {
            aList.add(Integer.parseInt(s));
        }
        try {
            articleServiceImpl.delAllArticle(aList);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("删除失败");
        }
        return BaseResult.ok("删除成功");
    }
    /**
     * 查询单个帖子
     */
    @GetMapping("/findOneArticleRyan/{aid}")
    public BaseResult findOneArticle(@PathVariable("aid") String aid){
        Article article = null;
        try {
            article = articleServiceImpl.findOneArticle(aid);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("查询单个帖子失败");
        }
        return BaseResult.ok("查询单个帖子成功",article);
    }
    /**
     * 根据用户ID删除用户所有的帖子
     */
    @DeleteMapping("/delByUidRyan/{uid}")
    public BaseResult delByUidRyan(@PathVariable("uid") Integer uid){
        try {
            articleServiceImpl.delByUidRyan(uid);
        } catch (Exception e) {
            return BaseResult.error("删除所有帖子失败");
        }
        return BaseResult.ok("删除所有帖子成功");
    }
}
