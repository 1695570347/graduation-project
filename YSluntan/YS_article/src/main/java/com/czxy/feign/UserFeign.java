package com.czxy.feign;

import com.czxy.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vo.BaseResult;

@FeignClient(value = "userservice",path = "/user")
public interface UserFeign {

    @GetMapping("/selectOneUserByUid/{uid}")
    public BaseResult<User> selectOneUser(@PathVariable("uid") Integer uid);

    @GetMapping("/selectOneUserByNickname/{nickname}")
    public BaseResult<User> selectOneUserByNickname(@PathVariable("nickname") String nickname);

}
