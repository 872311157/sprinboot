package com.example.springboot.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BaseDao implements IBaseDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryList(String tableName) {
        String sql = "select * from " + tableName;
        return this.jdbcTemplate.queryForList(sql);
    }
}
