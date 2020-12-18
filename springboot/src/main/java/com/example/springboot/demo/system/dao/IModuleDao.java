package com.example.springboot.demo.system.dao;

import com.example.springboot.demo.dao.IBaseDao;
import com.example.springboot.demo.system.entity.BootModule;

import java.util.List;

public interface IModuleDao extends IBaseDao {
    public List<BootModule> queryByUserid(Integer userid);

    /**
     * 根据模块id查询子模块
     * @param id
     * @return
     */
    public List<BootModule> queryChildsById(Integer id);
}
