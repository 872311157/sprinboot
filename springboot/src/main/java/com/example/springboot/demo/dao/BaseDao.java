package com.example.springboot.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.system.entity.BootModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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

    @Override
    public List<T> queryBySql(String sql) {
        return (List<T>) this.jdbcTemplate.queryForList(sql);
    }
}
