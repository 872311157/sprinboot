package com.example.springboot.demo.system.service;

import com.example.springboot.demo.system.entity.BootModule;

import java.util.List;

public interface IModuleService {
    /**
     * 根据userid查询菜单
     * @param userid
     * @return
     */
    public List<BootModule> queryByUserid(Integer userid);
}