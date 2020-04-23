package com.czxy.dao;

import com.czxy.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import vo.UserVo;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {

    @Select("select * from user where username = #{username} and password = #{password}")
    User login(User user);

    @Select("select * from user where emailKey = #{emailKey}")
    User selectWithEmailKey(@Param("emailKey") String emailKey);

    @Delete("delete from user where emailKey = #{emailKey}")
    void deleteByemailKey(@Param("emailKey") String emailKey);

    @Select("<script> select * from user " +
            "where 1 = 1 " +
            "<if test=\" nickname != null and nickname != '' and nickname != '%%'\"> and nickname like #{nickname} </if>" +
            "</script>")
    List<User> findBySelectPlate(UserVo userVo);

}
