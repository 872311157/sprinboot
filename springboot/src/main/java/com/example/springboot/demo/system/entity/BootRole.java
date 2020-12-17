package com.example.springboot.demo.system.entity;

/**
 * TODO
 * 角色表
 * @ClassName BootUser
 * @Date 2020-9-23 14:56
 * @Version 1.0
 */
public class BootRole {
    private Integer id;
    private String rolename;//角色名称
    private Integer roletype;//角色类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getRoletype() {
        return roletype;
    }

    public void setRoletype(Integer roletype) {
        this.roletype = roletype;
    }
}
