package com.example.springboot.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 15:44
 */
public class UserInfo {
    //@JsonIgnore
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String workNo;

    public UserInfo(){}

    public UserInfo(String name, String sex, Integer age, String workNo) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.workNo = workNo;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", workNo='" + workNo + '\'' +
                '}';
    }
}
