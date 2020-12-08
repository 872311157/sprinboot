package com.example.springboot.demo.util.dateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间管理控件
 */
public class DateUtils {
    private static SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前时间
     * @param pattern
     * @return
     */
    private static String getNowStr(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

}
