package com.example.springboot.demo.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.system.entity.BootModule;

import java.util.List;

public interface IModuleService {
    /**
     * 根据userid查询菜单
     * @param userid
     * @return
     */
    public JSONArray queryByUserid(Integer userid);
    /**
     * 根据模块id查询子模块
     * @param id
     * @return
     */
    public List<BootModule> queryChildsById(Integer id);
}
