package com.example.springboot.demo.controller;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 15:26
 */
@Api(description = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value = "查询用户", notes="通过id标识查询用户")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", required = true, dataType = "Integer")
    @RequestMapping(value = "getUser", method= RequestMethod.GET)
    public UserInfo GetUser(@RequestParam(name = "id")Integer id){
        System.out.println(id);
        UserInfo user = userInfoService.queryUserById(id);

        return user;
    }

    @ApiOperation(value = "新增用户信息", notes="新增用户信息")
    @PutMapping("insert")
    public Integer addUser(@RequestBody UserInfo userInfo){
        /*UserInfo user = new UserInfo();
        user.setName("小武");
        user.setSex("男");*/
        Integer i = userInfoService.insertUserInfo(userInfo);
        System.out.println(i);
        return i;
    }
}
