package com.example.springboot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.entity.WorkCalendar;
import com.example.springboot.demo.service.UserInfoService;
import com.example.springboot.demo.service.WorkCalendarService;
import com.example.springboot.demo.util.calendarUtils.HolidayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/calendar")
public class WorkCalendarController {
    private SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-M-d");

	@Autowired
	WorkCalendarService workCalendarService;

    @RequestMapping("")
	public String calendar(){
	    return "calendar/calendar";
    }

	@ResponseBody
    @RequestMapping("/querylist")
    public Map<String, Object> querylist(@RequestParam Map<String, String> param){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", "0");
        int year = Integer.parseInt(param.get("year"));
        int month = Integer.parseInt(param.get("month"));
        List<WorkCalendar> list = this.workCalendarService.queryList(year, month);
        if(null != list && list.size() > 0){
            result.put("list", list);
        }else{
            result.put("code", "-1");
        }
        return result;
    }

    @RequestMapping("/init")
    public void init(@RequestParam Map<String, String> param){
        int year = Integer.parseInt(param.get("year"));
		this.workCalendarService.initCalendarDatas(year);
    }
}
