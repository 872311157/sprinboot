package com.example.springboot.demo.system.dao;

import com.example.springboot.demo.dao.BaseDao;
import com.example.springboot.demo.system.entity.BootRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao extends BaseDao<BootRole> implements IRoleDao{

    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<BootRole> queryList() {
        return this.queryList("bootrole");
    }
}
