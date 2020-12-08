package com.example.springboot.demo.mapper;

import com.example.springboot.demo.entity.WorkCalendar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkCalendarMapper {
    /**
     * 新增日历
     * @param list
     * @return
     */
    public int insertBatch(List<WorkCalendar> list);

    /**
     * 查询日历数据
     * @param year
     * @param month
     * @return
     */
    public List<WorkCalendar> queryList(int year, int month);
}
