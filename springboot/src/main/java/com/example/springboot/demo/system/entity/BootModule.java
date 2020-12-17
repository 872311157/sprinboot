package com.example.springboot.demo.system.entity;

/**
 * TODO
 * 模型表
 * @ClassName BootUser
 * @Date 2020-9-23 14:56
 * @Version 1.0
 */
public class BootModule {
    private Integer id;
    private String modulename;//模型名称
    private String moduleaddress;//模型地址
    private Integer parentid;//父模型id
    private Integer mtype;//模型类型0分类，1引用

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getModuleaddress() {
        return moduleaddress;
    }

    public void setModuleaddress(String moduleaddress) {
        this.moduleaddress = moduleaddress;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }
}
