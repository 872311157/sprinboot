package com.example.springboot.demo.mapper;

import com.example.springboot.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 15:55
 */
@Mapper
@Repository
public interface UserInfoMapper {
    UserInfo Sel(int id);

    public Integer add(UserInfo userInfo);

    /**
     * 根据名称查询用户
     * @param name
     * @return
     */
    List<UserInfo> queryUserInfos(String username);

    /**
     * 查询所有
     * @return
     */
    List<UserInfo> queryAll();
}
