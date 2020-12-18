package com.example.springboot.demo.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
    /**
     * 查询列表
     * @param tableName
     * @return
     */
    public List<T> queryList(String tableName);

    /**
     * 根据sql语句查询
     * @param sql
     * @return
     */
    public List<T> queryBySql(String sql);
}
