package com.example.springboot.demo.system.service;

import com.example.springboot.demo.system.dao.IModuleDao;
import com.example.springboot.demo.system.entity.BootModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService implements IModuleService{

    @Autowired
    private IModuleDao moduleDao;


    @Override
    public List<BootModule> queryByUserid(Integer userid) {
        return this.moduleDao.queryByUserid(userid);
    }
}
