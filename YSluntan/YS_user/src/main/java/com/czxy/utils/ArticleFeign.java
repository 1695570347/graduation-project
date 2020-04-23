package com.czxy.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vo.BaseResult;

@FeignClient(value = "articleservice",path = "/article")
public interface ArticleFeign {

    @DeleteMapping("/delByUidRyan/{uid}")
    public BaseResult delByUidRyan(@PathVariable("uid") Integer uid);

}
