package com.example.springboot.demo.util.configUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-9-15 11:42
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")//prefix 前缀ignoreInvalidFields 用于指示绑定到此对象时应忽略无效字段的标志。ignoreUnknownFields 用于指示绑定到此对象时应忽略未知字段的标志。
public class ConfigProperties {
    //@Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String className;
    //@Value("${spring.datasource.username}")
    private String username;
    //@Value("${spring.datasource.password}")
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "url='" + url + '\'' +
                ", className='" + className + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
