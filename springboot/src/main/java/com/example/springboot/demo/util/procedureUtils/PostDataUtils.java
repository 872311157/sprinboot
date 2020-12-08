package com.example.springboot.demo.util.procedureUtils;

import com.example.springboot.demo.util.dataUtil.DataUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * TODO
 *
 * @Author hanlulu
 * @ClassName PostDataUtils
 * @Date 2020-9-23 16:54
 * @Version 1.0
 */
public class PostDataUtils {
    /**
     * @Author hanlulu
     * @Description 执行存储过程(返回的是游标)
     * @Date  2020-9-23
     * @Param [fName：存储过程名称, params：入参]
     * @return EntityBean[]
     **/
    public static List<Map<String, Object>> excuteByRefcursor(JdbcTemplate jdbcTemplate, String fName, PostParameters params)
    {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) jdbcTemplate.execute(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement statement = null;
				try {
					//处理存储过程方法名
					String procName = handleProcedureName(fName, params, true);
					con.setAutoCommit(false);
					statement = con.prepareCall(procName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return statement;
			}
		}, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement statement) throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if (null != statement)
				{
					//注册输出参数（游标）
					statement.registerOutParameter(1, Types.OTHER);
					Boolean flag = registerParameter(statement, params, true);
					if (flag)
					{
						// 参数的索引值是根据占位符?出现的次序从左到右由1开始计，不管其是输入还是输出参数
						statement.execute();
						ResultSet result = (ResultSet) statement.getObject(1);
						list = handleResult(result);
						//con.commit();//事务提交
					}
				}
				return list;
			}
		});

		return resultList;
    }

	/**
	 * @Author hanlulu
	 * @Description 执行存储过程(返回结果集)
	 * @Date  2020-9-23
	 * @Param [fName：存储过程名称, params：入参]
	 * @return EntityBean[]
	 **/
	public static List<Map<String, Object>> excuteByProcedure(JdbcTemplate jdbcTemplate, String fName, PostParameters params)
	{
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) jdbcTemplate.execute(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				//处理存储过程方法名
				String procName = handleProcedureName(fName, params, false);
				//设置不自动提交
				con.setAutoCommit(false);
				//预编译SQL语句，返回CallableStatement对象
				CallableStatement statement = con.prepareCall(procName);
				return statement;
			}
		}, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement statement) throws SQLException, DataAccessException {
				List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
				Boolean flag = registerParameter(statement, params, false);
				//发送参数，执行函数，接收结果（参数的索引值是根据占位符?出现的次序从左到右由1开始计，不管其是输入还是输出参数）
				statement.execute();
				ResultSet result = statement.getResultSet();
				//处理结果集
				table = handleResult(result);
				return table;
			}
		});
		return resultList;
	}

	/**
	 * @Author hanlulu
	 * @Description 处理存储过程方法名称
	 * @Date  2020-9-24
	 * @Param [fName, params, iscursor：返回结果集是否是游标（true:是，false:否）]
	 * @return java.lang.String
	 **/
	private static String handleProcedureName(String fName, PostParameters params, Boolean iscursor)
	{
		String s = iscursor? "{? = call " : "{call ";
		StringBuffer sql = new StringBuffer(s).append(fName).append("(");
		//拼接SQL
		if (null != params)
		{
			int num = params.getParams().size();
			for (int i=0; i<num; i++)
			{
				String p = (i==num-1)?"?":"?,";
				sql.append(p);
			}
		}
		sql.append(")}");
		return sql.toString();
	}

    /**
     * @Author hanlulu
     * @Description 处理结果集
     * @Date  2020-9-23
     * @Param [result]
     * @return EntityBean[]
     **/
	private static List<Map<String, Object>> handleResult(ResultSet result)
	{
        List<Map<String, Object>> table = null;

		try {
			ResultSetMetaData rsmd = result.getMetaData();
			//获取列数
			int count=rsmd.getColumnCount();
			//获取列名
			LinkedList<String> colName = new LinkedList<String>();
			for(int i=0;i<count;i++)
			{
				String key = rsmd.getColumnName(i+1);//获取指定列的名称。
				colName.add(key);
			}
			table = new LinkedList<Map<String, Object>>();
			//根据列名，取值，存到table集合
			while (result.next()) {
                Map<String, Object> obj = new HashMap<String, Object>();
				for (String key : colName)
				{
					String value = result.getString(key);
					if (StringUtils.isEmpty(value))
					{
						value = "";
					}
					obj.put(key, value);
				}
				table.add(obj);
			}
			//table;//table.toArray(new EntityBean[table.size()]);
		} catch (SQLException e) {
			//Global.getInstance().LogError("处理结果集失败");
			e.printStackTrace();
		}
        return table;
	}

    /**
     * @Author hanlulu
     * @Description 注册参数
     * @Date  2020-9-23
     * @Param [st, params, iscursor：返回结果集是否是游标（true:是，false:否）]
     * @return java.lang.Boolean
     **/
	private static Boolean registerParameter(CallableStatement st, PostParameters params, Boolean iscursor)
	{
		Boolean reult = true;
		Integer defaultIndex = 0;
		try {
			if(null != params)
			{
				//判断返回的结果集是否是游标（参数占位符从2开始）
				if (iscursor)
				{
					defaultIndex = 1;
				}
				Map<String, ProcParameter> paramMap = params.getParams();

				for (String key : paramMap.keySet())
				{
					Integer index = paramMap.get(key).getIndex() + defaultIndex;//参数位置
					Class<?> type = paramMap.get(key).getType();//参数数据类型
					String dirction = paramMap.get(key).getDirction();//参数类型（in、out）
					int typeid = getDatabaseType(type);
					if("IN".equalsIgnoreCase(dirction))
					{
						Object value = paramMap.get(key).getValue();//参数值
						st.setObject(index, value, typeid);
					}
				}
			}
		} catch (SQLException e) {
			reult = false;
			e.printStackTrace();
		}

		return reult;
	}

    /**
     * @Author hanlulu
     * @Description 获取数据类型
     * @Date  2020-9-23
     * @Param [type]
     * @return int
     **/
	private static int getDatabaseType(Class<?> type)
	{
		if(type == String.class)
		{
			return Types.VARCHAR;
		}
		else if(type == Integer.class )
		{
			return Types.INTEGER;
		}
		else if(type == Short.class)
		{
			return Types.INTEGER;
		}
		else if(type == Date.class)
		{
			return Types.DATE;
		}
		return Types.VARCHAR;
	}

	public static void main(String[] args) {
		/*String fname = "lpom_validate_user";
		PostParameters params = new PostParameters();
		params.addStringParameter(1, "inusername", "韩路路");
		List<Map<String, Object>> list = PostDataUtils.excuteByProcedure(this.jdbcTemplate,fname,params);
		for (Map<String, Object> map : list)
		{
			System.out.println(map.get("ousername"));
		}*/
		//String fname = "lpom_getusertable";
		//String fname = "lpom_getuserlist";
		//List<Map<String, Object>> list = PostDataUtils.queryByRefcursor(this.jdbcTemplate,fname,params);
//        for (Map<String, Object> map : list)
//        {
//            System.out.println(map.get("username"));
//        }
	}
}
