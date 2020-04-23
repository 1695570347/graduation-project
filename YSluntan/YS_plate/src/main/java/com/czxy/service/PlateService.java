package com.czxy.service;

import com.czxy.domain.Plate;
import com.github.pagehelper.PageInfo;
import vo.PlateVo;

import java.util.ArrayList;
import java.util.List;

public interface PlateService {

    void addPlate(Plate plate);

    void editPlate(Plate plate);

    void delAllPlate(ArrayList<Integer> pList);

    Plate findOnePlate(String pid);

    PageInfo<Plate> findBySelectPlate(PlateVo plateVo);

    List<Plate> findAllPlate();

}
