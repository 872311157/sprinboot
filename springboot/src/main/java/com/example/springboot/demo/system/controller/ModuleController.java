package com.example.springboot.demo.system.controller;

import com.example.springboot.demo.system.entity.BootModule;
import com.example.springboot.demo.system.entity.BootRole;
import com.example.springboot.demo.system.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * TODO
 * 菜单模型控制器
 * @author 韩路路
 */
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    /**
     * 通过用户id查询角色权限
     * @param param
     * @return
     */
    @RequestMapping("queryByUserid")
    public List<BootModule> queryByUserid(@RequestParam Map<String, String> param){
        Integer userid = Integer.parseInt(param.get("userid"));
        List<BootModule> modules = this.moduleService.queryByUserid(userid);
        return modules;
    }

    /**
     * 根据模块id查询子模块
     * @param param
     * @return
     */
    @RequestMapping("queryChildsById")
    public List<BootModule> queryChildsById(@RequestParam Map<String, String> param){
        Integer id = Integer.parseInt(param.get("id"));
        List<BootModule> modules = this.moduleService.queryChildsById(id);
        return modules;
    }
}
