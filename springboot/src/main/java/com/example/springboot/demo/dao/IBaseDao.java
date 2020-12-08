package com.example.springboot.demo.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao {
    public List<Map<String, Object>> queryList(String tableName);
}
