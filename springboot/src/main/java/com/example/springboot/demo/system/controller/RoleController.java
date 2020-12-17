package com.example.springboot.demo.system.controller;

import com.example.springboot.demo.system.entity.BootModule;
import com.example.springboot.demo.system.entity.BootRole;
import com.example.springboot.demo.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * TODO
 * 用户角色控制器
 * @author 韩路路
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    public IRoleService roleService;

    /**
     * 查询列表
     * @return
     */
    @RequestMapping("querylist")
    public List<BootRole> queryList(){
        List<BootRole> list = this.roleService.queryList();
        return list;
    }

}
