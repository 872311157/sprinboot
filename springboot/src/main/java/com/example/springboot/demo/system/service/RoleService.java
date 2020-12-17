package com.example.springboot.demo.system.service;

import com.example.springboot.demo.system.dao.IRoleDao;
import com.example.springboot.demo.system.entity.BootRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<BootRole> queryList() {
        List<BootRole> list = this.roleDao.queryList();
        return list;
    }
}
