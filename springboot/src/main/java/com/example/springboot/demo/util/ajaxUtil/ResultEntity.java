package com.example.springboot.demo.util.ajaxUtil;


import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultEntity<T> {
    private Boolean result;
    private Integer code;
    private List<T> data;
    private String message;

    public static ResultEntity SUCCESS_RESULT = new ResultEntity(true, 0, null, "success");
    public static ResultEntity ERROR_RESULT = new ResultEntity(false, -1, null, "error");

/*    public static ResultEntity getSuccessResult(){
        return new ResultEntity(true, 0, null, "success");
    }

    public static ResultEntity getErrorResult(){
        return new ResultEntity(false, -1, null, "error");
    }*/

/*    public static ResultEntity getErrorResult(String message){
        String mes = StringUtils.isEmpty(message)?"error":message;
        return new ResultEntity(false, -1, null, mes);
    }*/

    public ResultEntity(Boolean result, Integer code, List<T> data, String message) {
        this.result = result;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setSuccessData(T t){
        List<T> list = new ArrayList<T>();
        list.add(t);
        this.SUCCESS_RESULT.setData(list);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
