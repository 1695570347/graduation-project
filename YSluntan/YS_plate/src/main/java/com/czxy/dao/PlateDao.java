package com.czxy.dao;

import com.czxy.domain.Plate;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import vo.PlateVo;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface PlateDao extends Mapper<Plate> {

    @Select("<script> select * from plate p,user u " +
            "where p.uid = u.uid " +
            "<if test=\" pname != null and pname != '%%' and pname != '' \"> and p.pname like #{pname} </if>" +
            "<if test=\" ptitle != null and ptitle != '%%' and ptitle != '' \"> and p.ptitle like #{ptitle} </if>" +
            "<if test=\" nickname != null and nickname != '%%' and nickname != '' \"> and nickname like #{nickname} </if>" +
            "</script>")
    List<Plate> findBySelectPlate(PlateVo plateVo);




//    @Insert("INSERT INTO plate VALUES(NULL,#{pname},#{uid},#{ptitle},#{pcontent},0,0)")
//    void insertMyPlate(Plate plate);



//    @Select("<script> select * from plate p,user u " +
//            "where p.uid = u.uid " +
//            "<if test=\" pname != null and pname != %% and pname != '' \"> and pname like #{pname} </if>" +
//            "<if test=\" ptitle != null and ptitle != %% and ptitle != '' \"> and ptitle like #{ptitle} </if>" +
//            "<if test=\" nickname != null and nickname != %% and nickname != '' \"> and nickname like #{nickname} </if>" +
//            "</script>")
}
