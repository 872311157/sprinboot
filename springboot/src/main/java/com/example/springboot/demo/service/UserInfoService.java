package com.example.springboot.demo.service;


import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 15:56
 */
@Service
public class UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo queryUserById(Integer id){
        return userInfoMapper.Sel(id);
    }

    public Integer insertUserInfo(UserInfo userinfo){
        return userInfoMapper.add(userinfo);
    }

    public List<UserInfo> queryUserInfos(String username){
        List<UserInfo> list = userInfoMapper.queryUserInfos(username);

        return list;
    }

    public List<UserInfo> queryAll(){
        List<UserInfo> list = userInfoMapper.queryAll();
        return list;
    }
}
