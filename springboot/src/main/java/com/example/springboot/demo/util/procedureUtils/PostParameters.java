package com.example.springboot.demo.util.procedureUtils;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * postgreSql入参对象
 * @author Administrator
 *
 */
public class PostParameters {
	
	private Map<String, ProcParameter> params = new HashMap<String,ProcParameter>();
	
	public void setParams(Map<String,ProcParameter> params)
	{
		this.params = params;
	}
	
	public Map<String,ProcParameter> getParams()
	{
		return params;
	}
	
	//------------入参------------------
	public void addParameter(Integer index, String name,Object value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value, value.getClass());
		addParameter(parameter);
	}
	
	public void addStringParameter(Integer index, String name, String value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value, String.class);
		addParameter(parameter);
	}
	
	public void addIntegerParameter(Integer index, String name, Integer value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value, Integer.class);
		addParameter(parameter);
	}
	
	public void addDateParameter(Integer index, String name, Date value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value, Date.class);
		addParameter(parameter);
	}
	
	public void addDoubleParameter(Integer index, String name, Date value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value, Double.class);
		addParameter(parameter);
	}
	
	public void addFloatParameter(Integer index, String name, Date value)
	{
		ProcParameter parameter = new ProcParameter(index, name, value,Float.class);
		addParameter(parameter);
	}
	public void removeParameter(String name)
	{
		params.remove(name);
	}
	
	public void addParameter(ProcParameter parameter)
	{
		if(null == parameter)
		{
			return ;
		}
		String paraName = parameter.getName();
		if(StringUtils.isEmpty(paraName))
		{
			return;
		}
		parameter.setDirction("IN");
		paraName = paraName.trim();
		params.put(paraName,parameter);
	}
	
	
	//------------出参------------------
	/*public void addOutParameter(Integer index, String name, Class<?> type)
	{
		ProcParameter parameter = new ProcParameter(index, name, type);
		addOutParameter(parameter);
	}
	
	public void addOutParameter(ProcParameter parameter)
	{
		if(null == parameter)
		{
			return ;
		}
		String paraName = parameter.getName();
		if(StringUtil.isEmpty(paraName))
		{
			return;
		}
		parameter.setDirction("OUT");
		paraName = paraName.trim();
		params.put(paraName,parameter);
	}*/
	
}
