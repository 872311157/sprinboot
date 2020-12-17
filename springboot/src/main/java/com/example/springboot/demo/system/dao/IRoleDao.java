package com.example.springboot.demo.system.dao;

import com.example.springboot.demo.dao.IBaseDao;
import com.example.springboot.demo.system.entity.BootRole;

import java.util.List;

public interface IRoleDao extends IBaseDao {
    public List<BootRole> queryList();
}
