package com.example.springboot.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.entity.WorkCalendar;
import com.example.springboot.demo.mapper.WorkCalendarMapper;
import com.example.springboot.demo.util.calendarUtils.HolidayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkCalendarService {

    private SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-M-d");
    private SimpleDateFormat sdf_ymmdd = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    public WorkCalendarMapper calendarMapper;

    /**
     * 初始化日历表
     * @param year
     * @return
     */
    public void initCalendarDatas(int year){
        try {
            //节假日信息
            Map<String, JSONObject> holidays = HolidayUtils.getInstance().getHolidays(year);
            LinkedList<WorkCalendar> list = new LinkedList<WorkCalendar>();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            int i = 0;//月份
            while(i < 12) {
                cal.set(Calendar.MONTH, i);
                System.out.println("=============="+this.sdf_ymd.format(cal.getTime()));
                //int month = cal.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
                //int curday = cal.getActualMinimum(Calendar.DAY_OF_MONTH);    //获取本月最小天数
                int month = i+1;
                int curday = 1;
                int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);    //获取本月最大天数
                while (curday <= last) {
                    WorkCalendar work = new WorkCalendar();
                    work.setWorkyear(year);
                    work.setWorkmonth(month);
                    work.setWorkday(curday);
                    work.setWorkstatus(0);//0工作日，1节假日，2周末
                    Date dayDate = sdf_ymd.parse(year + "-" + month + "-" + curday);
                    work.setWorkdate(dayDate);
                    // 判断周末
                    Calendar dayCal = Calendar.getInstance();
                    dayCal.setTime(dayDate);
                    // 星期数
                    int week = dayCal.get(Calendar.DAY_OF_WEEK) - 1;
                    work.setWorkweek(week);

                    String dayStr = sdf_ymd.format(dayDate);
                    //当天节假日信息
                    JSONObject dayinfo = holidays.get(dayStr);
                    if (Optional.ofNullable(dayinfo).isPresent()) {
                        String name = dayinfo.getString("name");
                        work.setHoliday(name);
                        Integer type = dayinfo.getInteger("type");
                        work.setWorkstatus(type);
                    }
                    list.add(work);
                    System.out.println(JSON.toJSONString(work));
                    curday ++;
                }
                i ++;
            }
            this.calendarMapper.insertBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 查询日历数据
     * @param year
     * @param month
     * @return
     */
    public List<WorkCalendar> queryList(int year, int month){
        return this.calendarMapper.queryList(year, month);
    }
}
