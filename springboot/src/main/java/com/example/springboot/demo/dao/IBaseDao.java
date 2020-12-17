package com.example.springboot.demo.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
    /**
     * 查询列表
     * @param tableName
     * @return
     */
    public List<T> queryList(String tableName);
}
