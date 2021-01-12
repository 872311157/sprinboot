package com.example.springboot.demo.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
     * 通过用户id查询角色菜单(限制三级菜单)
     * @param param
     * @return
     */
    @RequestMapping("queryByUserid")
    public JSONArray queryByUserid(@RequestParam Map<String, String> param){
        Integer userid = Integer.parseInt(param.get("userid"));
        JSONArray modules = this.moduleService.queryByUserid(userid);
        for(Object obj : modules){
            if(null != obj){
                JSONObject module = (JSONObject)obj;
                String name = module.getString("modulename");
                int id = module.getInteger("id");
                JSONArray nodes = new JSONArray();
                queryChildModule(nodes, id);
                module.put("nodes", nodes);
            }
        }
        return modules;
    }

    /**
     * 递归查询菜单下的所有子菜单
     * @param id
     * @return
     */
    public void queryChildModule(JSONArray nodes, Integer id){
        List<BootModule> modules = this.moduleService.queryChildsById(id);
        for(BootModule module : modules){
            JSONObject node = new JSONObject();
            String name = module.getModulename();
            node.put("name", name);
            node.put("id", id);
            //0分类，1引用
            if(module.getMtype() == 0){
                JSONArray childs = new JSONArray();
                queryChildModule(childs, module.getId());
                node.put("nodes", childs);
            }
            nodes.add(node);
        }
    }
}
