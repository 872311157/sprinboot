package com.example.springboot.demo.service;

import com.example.springboot.demo.entity.BootUser;
import com.example.springboot.demo.mapper.BootUserMapper;
import com.example.springboot.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author hanlulu
 * @ClassName BootUserService
 * @Date 2020-9-23 15:21
 * @Version 1.0
 */
@Service
public class BootUserService {
    @Autowired
    BootUserMapper bootUserMapper;

    /**
     * @Author hanlulu
     * @Description 
     * @Date  2020-9-23
     * @Param [username, password]
     * @return com.example.springboot.demo.entity.BootUser
     **/
    public BootUser login(String username, String passwords)
    {
        return this.bootUserMapper.login(username, passwords);
    }

    /**
     * @Author hanlulu
     * @Description
     * @Date  2020-9-23
     * @Param [user]
     * @return java.lang.Integer
     **/
    public Integer insert(BootUser user){
        return this.bootUserMapper.insert(user);
    }
}
