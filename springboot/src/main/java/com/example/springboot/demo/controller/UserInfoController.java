package com.example.springboot.demo.controller;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 17:46
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    //http://localhost:8083/ssm/userInfo/getUserInfo/2
    @RequestMapping("/getUser/{id}")
    public UserInfo GetUser(@PathVariable(name="id")Integer id){
        System.out.println(id);
        UserInfo user = userInfoService.queryUserById(id);
        return user;
    }

    //http://localhost:8083/ssm/userInfo/getUserInfo?id=2
    @RequestMapping(value = "/getUserInfo", method= RequestMethod.GET)
    public UserInfo GetUser1(@RequestParam("id")Integer id){
        System.out.println(id);
        UserInfo user = userInfoService.queryUserById(id);
        return user;
    }

    // http://localhost:8083/ssm/userInfo/queryUsersByName?age=18
    @RequestMapping(value = "/queryUsersByName", method= RequestMethod.GET)
    public List<UserInfo> queryByName(@RequestParam("username")String username){
        System.out.println(username);
        List<UserInfo> users = userInfoService.queryUserInfos(username);
        return users;
    }

    @RequestMapping("/queryAll")
    public List<UserInfo> queryAll(){
        List<UserInfo> list = userInfoService.queryAll();
        return list;
    }
}
