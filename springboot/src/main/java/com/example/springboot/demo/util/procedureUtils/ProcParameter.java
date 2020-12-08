package com.example.springboot.demo.util.procedureUtils;

/**
 * 
 * 存储过程的入参实体类
 * @author 韩路路
 *
 */
public class ProcParameter {
	private Integer index;//参数位置
	private String dirction;//in 输入参数，out 输出参数
	private Class<?> type;//参数类型
	private String name;//参数名称
	private Object value;//参数值
	
	public ProcParameter(){};
	public ProcParameter(Integer index, String name, Class<?> type) {
		super();
		this.index = index;
		this.name = name;
		this.type = type;
	}
	public ProcParameter(Integer index, String name, Object value, Class<?> type) {
		super();
		this.index = index;
		this.name = name;
		this.value = value;
		this.type = type;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getDirction() {
		return dirction;
	}
	public void setDirction(String dirction) {
		this.dirction = dirction;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
