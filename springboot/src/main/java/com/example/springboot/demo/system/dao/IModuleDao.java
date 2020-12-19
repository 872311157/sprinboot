package com.example.springboot.demo.system.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.dao.IBaseDao;
import com.example.springboot.demo.system.entity.BootModule;

import java.util.List;

public interface IModuleDao extends IBaseDao {
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
