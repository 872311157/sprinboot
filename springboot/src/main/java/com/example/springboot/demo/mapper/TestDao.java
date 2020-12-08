package com.example.springboot.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TestDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryTable(String tablename){
        String sql = "select * from " + tablename;
        return jdbcTemplate.queryForList(sql);
    }

}
