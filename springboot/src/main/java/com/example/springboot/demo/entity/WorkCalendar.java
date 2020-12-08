package com.example.springboot.demo.entity;

import java.util.Date;

/**
 * 日历
 */
public class WorkCalendar {
    private Integer id;
    private Integer workyear;
    private Integer workmonth;
    private Integer workday;
    private Integer workweek;//星期几
    private Date workdate;
    private String holiday;//节假日
    private Integer workstatus;//0工作日，1节假日，2周末

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkyear() {
        return workyear;
    }

    public void setWorkyear(Integer workyear) {
        this.workyear = workyear;
    }

    public Integer getWorkmonth() {
        return workmonth;
    }

    public void setWorkmonth(Integer workmonth) {
        this.workmonth = workmonth;
    }

    public Integer getWorkday() {
        return workday;
    }

    public void setWorkday(Integer workday) {
        this.workday = workday;
    }

    public Integer getWorkweek() {
        return workweek;
    }

    public void setWorkweek(Integer workweek) {
        this.workweek = workweek;
    }

    public Date getWorkdate() {
        return workdate;
    }

    public void setWorkdate(Date workdate) {
        this.workdate = workdate;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public Integer getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(Integer workstatus) {
        this.workstatus = workstatus;
    }
}
