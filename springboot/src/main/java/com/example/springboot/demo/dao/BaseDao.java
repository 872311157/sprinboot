package com.example.springboot.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BaseDao<T> implements IBaseDao{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List<T> queryList(String tableName) {
        String sql = "select * from " + tableName;
        return (List<T>) this.jdbcTemplate.queryForList(sql);
    }
}
