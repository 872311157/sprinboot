package com.example.springboot.demo.system.dao;

import com.example.springboot.demo.dao.BaseDao;
import com.example.springboot.demo.system.entity.BootModule;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ModuleDao extends BaseDao<BootModule> implements IModuleDao{

    @Override
    public List<BootModule> queryByUserid(Integer userid) {
        String sql = "select * from view_usermodules where userid=?";

        List<BootModule> mlist = null;
        mlist = (List<BootModule>) this.jdbcTemplate.query(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, userid);
            }
        }, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<BootModule> list = new LinkedList<BootModule>();
                while (rs.next()) {
                    BootModule module = new BootModule();
                    module.setId(rs.getInt("id"));
                    module.setModulename(rs.getString("modulename"));
                    module.setModuleaddress(rs.getString("moduleaddress"));
                    module.setParentid(rs.getInt("parentid"));
                    module.setMtype(rs.getInt("mtype"));
                    list.add(module);
                }
                return list;
            }
        });
        return mlist;
    }
}