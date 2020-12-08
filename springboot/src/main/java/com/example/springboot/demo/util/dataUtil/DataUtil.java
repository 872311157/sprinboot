package com.example.springboot.demo.util.dataUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO
 *
 * @Author hanlulu
 * @ClassName DataUtil
 * @Date 2020-9-23 16:47
 * @Version 1.0
 */
@Component
public class DataUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Connection getConnection(){
        Connection con = null;
        try {
            DataSource dataSource = this.jdbcTemplate.getDataSource();
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
