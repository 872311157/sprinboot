package com.example.springboot.demo.system.dao;

import com.example.springboot.demo.dao.IBaseDao;
import com.example.springboot.demo.system.entity.BootModule;

import java.util.List;

public interface IModuleDao extends IBaseDao {
    public List<BootModule> queryByUserid(Integer userid);
}
