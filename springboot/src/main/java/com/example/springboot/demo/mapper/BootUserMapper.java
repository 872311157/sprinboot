package com.example.springboot.demo.mapper;

import com.example.springboot.demo.entity.BootUser;
import com.example.springboot.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * @Author hanlulu
 * @ClassName BootUserMapper
 * @Date 2020-9-23 15:05
 * @Version 1.0
 */
@Mapper
@Repository
public interface BootUserMapper {
    /**
     * @Author hanlulu
     * @Description 
     * @Date  2020-9-23
     * @Param [username, password]
     * @return com.example.springboot.demo.entity.BootUser
     **/
    public BootUser login(String username, String passwords);

    /**
     * @Author hanlulu
     * @Description 
     * @Date  2020-9-23
     * @Param [user]
     * @return java.lang.Integer
     **/
    public Integer insert(BootUser user);
}
