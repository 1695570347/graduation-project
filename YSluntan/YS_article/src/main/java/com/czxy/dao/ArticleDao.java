package com.czxy.dao;

import com.czxy.domain.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import vo.ArticleVo;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ArticleDao extends Mapper<Article> {

    @Select("<script> select * from article a,plate p,USER u " +
            "WHERE a.pid = p.pid AND p.uid = u.uid " +
            "<if test=\" atitle != '' and atitle != '%%' and atitle != null \"> and a.atitle like #{atitle}</if>" +
            "<if test=\" nickname != '' and nickname != '%%' and nickname != null \"> and u.nickname like #{nickname}</if>" +
            "<if test=\" pid != null \"> and p.pid = #{pid}</if>" +
            "</script>")
    List<Article> findBySelectArticle(ArticleVo articleVo);

    @Delete("delete from article where uid = #{uid}")
    void deleteByUid(@Param("uid") Integer uid);

}
