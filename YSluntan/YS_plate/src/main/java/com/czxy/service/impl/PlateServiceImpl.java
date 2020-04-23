package com.czxy.service.impl;

import com.czxy.dao.PlateDao;
import com.czxy.domain.Plate;
import com.czxy.domain.User;
import com.czxy.feign.UserFeign;
import com.czxy.service.PlateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vo.BaseResult;
import vo.PlateVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlateServiceImpl implements PlateService {
    @Resource
    private PlateDao plateDao;
    @Resource
    private UserFeign userFeign;

    /**
     * 添加板块
     */
    @Override
    public void addPlate(Plate plate) {
        BaseResult<User> user = userFeign.selectOneUserByNickname(plate.getNickname());
        if (user.getData() == null){
            throw new RuntimeException("没有叫"+plate.getNickname()+"的版主");
        }else{
            //缺少一个uid,这里没有添加uid
            try {
                //添加一个uid给plate
                plate.setUid(user.getData().getUid());
                plateDao.insert(plate);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("添加异常,请找后台人员");
            }
        }
    }
    /**
     * 编辑板块
     */
    @Override
    public void editPlate(Plate plate) {
        plateDao.updateByPrimaryKeySelective(plate);
    }
    /**
     * 删除板块
     */
    @Override
    public void delAllPlate(ArrayList<Integer> pList) {
        for (Integer pid : pList) {
            plateDao.deleteByPrimaryKey(pid);
        }
    }
    /**
     * 查询单个板块
     * @return
     */
    @Override
    public Plate findOnePlate(String pid) {
        //只查出了板块信息
        Plate plate = plateDao.selectByPrimaryKey(pid);
        //查询用户信息
        BaseResult<User> user = userFeign.selectOneUser(plate.getUid());
        plate.setUser(user.getData());
        return plate;
    }

    @Override
    public PageInfo<Plate> findBySelectPlate(PlateVo plateVo) {
        //开启分页助手
        PageHelper.startPage(plateVo.getPageNum(),plateVo.getPageSize());
        //模糊查询
        plateVo.setNickname("%"+plateVo.getNickname()+"%");
        plateVo.setPname("%"+plateVo.getPname()+"%");
        plateVo.setPtitle("%"+plateVo.getPtitle()+"%");
        List<Plate> plist = plateDao.findBySelectPlate(plateVo);
        //因为上面只有板块信息,但是要显示板主,所以要通过远程调用高度方式拿到版主的部分信息
        for (Plate plate : plist) {
            //通过uid给板块附加版主信息
            BaseResult<User> user = userFeign.selectOneUser(plate.getUid());
            plate.setUser(user.getData());
        }
        PageInfo<Plate> pi = new PageInfo<>(plist);
        return pi;
    }

    @Override
    public List<Plate> findAllPlate() {
        return plateDao.selectAll();
    }
}
