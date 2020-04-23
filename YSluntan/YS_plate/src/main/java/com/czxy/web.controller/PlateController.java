package com.czxy.web.controller;

import com.czxy.domain.Plate;
import com.czxy.service.PlateService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import vo.BaseResult;
import vo.PlateVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/plate")
public class PlateController {
    @Resource
    private PlateService plateServiceImpl;

    /**
     * 添加板块
     */
    @PostMapping("/addPlateRyan")
    public BaseResult addPlate(@RequestBody Plate plate){
        try {
            plateServiceImpl.addPlate(plate);
        } catch (Exception e) {
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.ok("添加成功");
    }

    /**
     * 分页查询板块
     */
    @PostMapping("/findBySelectPlateRyan")
    public BaseResult findBySelectPlate(@RequestBody PlateVo plateVo){
        //创建分页对象
        PageInfo<Plate> pi = null;
        try {
            pi = plateServiceImpl.findBySelectPlate(plateVo);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("分页查询失败");
        }
        return BaseResult.ok("分页查询成功",pi);
    }
    /**
     * 编辑板块
     */
    @PutMapping("/editPlateRyan")
    public BaseResult editPlate(@RequestBody Plate plate){
        try {
            plateServiceImpl.editPlate(plate);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("编辑失败");
        }
        return BaseResult.ok("编辑成功");
    }
    /**
     * 删除板块
     */
    @DeleteMapping("/delAllPlateRyan/{pidList}")
    public BaseResult delAllPlate(@PathVariable("pidList") String pidList){
        //接收到的是一个集合字符串,切割","即可
        String[] split = pidList.split(",");
        ArrayList<Integer> pList = new ArrayList<>();
        for (String s : split) {
            pList.add(Integer.parseInt(s));
        }
        try {
            plateServiceImpl.delAllPlate(pList);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("删除失败");
        }
        return BaseResult.ok("删除成功");
    }
    /**
     * 查询单个板块
     */
    @GetMapping("/findOnePlateRyan/{pid}")
    public BaseResult findOnePlate(@PathVariable("pid") String pid){
        Plate plate = null;
        try {
            plate = plateServiceImpl.findOnePlate(pid);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("查询单个板块失败");
        }
        return BaseResult.ok("查询单个板块成功",plate);
    }
    /**
     * 查询所有的板块
     */
    @GetMapping("/findAllPlateRyan")
    public BaseResult findAllPlate(){
        List<Plate> plist = null;
        try {
            plist = plateServiceImpl.findAllPlate();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error("查询所有板块失败");
        }
        return BaseResult.ok("查询所有板块成功",plist);
    }
}
