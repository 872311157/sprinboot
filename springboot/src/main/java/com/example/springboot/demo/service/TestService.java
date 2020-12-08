package com.example.springboot.demo.service;

import com.example.springboot.demo.dao.IBaseDao;
import com.example.springboot.demo.mapper.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    TestDao testDao;

    @Autowired
    IBaseDao baseDao;

    //public List<Map<String, Object>> queryTable(String tablename){
//        return this.testDao.queryTable(tablename);
//    }

    public List<Map<String, Object>> queryTable(String tablename){
        return this.baseDao.queryList(tablename);
    }

}
