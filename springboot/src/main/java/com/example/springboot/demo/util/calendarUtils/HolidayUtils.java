package com.example.springboot.demo.util.calendarUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.demo.util.httpUtils.HttpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HolidayUtils {
	private static SimpleDateFormat sdf_ymd0 = new SimpleDateFormat("yyyy-M-d");
	private static SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static String BAIDU_URL = "http://opendata.baidu.com/api.php";
	private static String HOLIDAY_PARAM = "query=YEAR_MONTH&resource_id=6018&format=json";
	private static HolidayUtils INSTANCE = null;

    private HolidayUtils() {
    }

    public static synchronized HolidayUtils getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new HolidayUtils();
        }
        return INSTANCE;
    }
	
	public static void main(String[] args) {
		//initCalendar();
		//createWorkCalendar(2020, 10);
		//HolidayUtils.queryHolidays("2019年9月");
	}

	/**
	 * 获取日历表数据
	 */
	private static void query_calendars(int year, int month){

	}
	
	/**
	 * 初始化日历表
	 * @param year
	 */
//	public static void initCalendar() {
//		try {
//			DataSource ds = Global.getInstance().getDataSource();
//			SimpleDateFormat sdf_yyy = new SimpleDateFormat("yyyy");
//			//获取年份
//			int year = Integer.valueOf(sdf_yyy.format(new Date()));
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.YEAR, year);
//
//			//日期详情
//			Map<String, Object> dayInfos = new HashMap<String, Object>();
//			//节假日信息
//			Map<String, Object> holidays = getHolidays(year);
//			Transaction tran = null;
//			boolean falg = true;
//			int i = 0;//月份
//			while(i < 12) {
//				cal.set(Calendar.MONTH, i);
//				int years = cal.get(Calendar.YEAR);
//				int month = cal.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
//				int first = cal.getActualMinimum(Calendar.DAY_OF_MONTH);    //获取本月最小天数
//				int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);    //获取本月最大天数
//				while (first <= last) {
//					if (tran == null) {
//						tran = ds.createTransaction();
//					}
//					//工作日历表
//					EntityBean dayInfo = new EntityBean();
//					dayInfo.setbeanname("lroaworkday");
//					Date dayDate = sdf_ymd.parse(years + "-" + month + "-" + first);
//					dayInfo.set("workdate", dayDate);
//					String day = sdf_ymd.format(dayDate);
//					//当天是节假日，或周末调休的上班工作日
//					EntityBean holiday = (EntityBean) holidays.get(day);
//					//type 大周工作日0,小周工作日1,休息日2,
//					dayInfo.set("worktype", 0);
//					if (Optional.ofNullable(holiday).isPresent()) {
//						String name = holiday.getString("name");
//						int type = holiday.getInt("type");
//						dayInfo.set("holiday", name);
//						dayInfo.set("worktype", type);
//					}
//					//System.out.println(JSONSerializer.getInstance().Serialize(dayInfo));
//					dayInfos.put(day, dayInfo);
//
//					falg = dayInfo.insert(tran);
//					if (!falg) {
//						break;
//					}
//					first ++;
//				}
//				i ++;
//			}
//
//			if (falg) {
//				tran.commit();
//			} else {
//				tran.rollback();
//			}
//
//			System.out.println(JSONSerializer.getInstance().Serialize(dayInfos));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Global.getInstance().LogError("初始化日历表数据方法initCalendar异常", e);
//		}
//
//	}
//
	/**
	 * 获取节假日信息
	 * @param year
	 * @return
	 */
	public static Map<String, JSONObject> getHolidays(int year){
		//节假日信息
		Map<String, JSONObject> holidayMap = new HashMap<String, JSONObject>();

		try {
			String year_month = year + "年";
			String param = HOLIDAY_PARAM.replace("YEAR_MONTH",year_month);
			JSONObject result = HttpUtil.sendGet(BAIDU_URL, param);
			if (Optional.ofNullable(result).isPresent()) {
				int status = result.getInteger("status");
				if (status == 0) {
					//System.out.println(JSON.toJSON(result));
					JSONArray datas = result.getJSONArray("data");
					for(Object obj : datas){
						JSONObject datesInfo = (JSONObject)obj;
						if (Optional.ofNullable(datesInfo).isPresent()) {
							//日期信息
							JSONArray almanac = datesInfo.getJSONArray("almanac");
							//holidaylist字段，节假日信息[{"name": "中秋节","startday": "2020-10-1"}],name节假日名称，startday开始日期
							JSONArray holidaylist = datesInfo.getJSONArray("holidaylist");
							//holiday字段，节假日信息详细信息，rest解释节假日，desc描述调班信息，status=1表示放假，status=2表示上班（周末调休的上班）
							JSONArray holidays = datesInfo.getJSONArray("holiday");

							for (Object holidayObj : holidays) {
								JSONObject holidayInfo = (JSONObject)holidayObj;
								//{"desc": "1月1日放假一天","festival": "2020-1-1","list": [{"date": "2020-1-1", "status": "1"}],"list#num#baidu": 1,"name": "元旦","rest": "2019年12月30日和2019年12月31日请假两天，与周末连休可拼5天小长假。"}
								String name = holidayInfo.getString("name");//节日名称
								String festival = holidayInfo.getString("festival");//节日开始日期

								//节日在同一天情况
								JSONObject sameDay = holidayMap.get(festival);
								if (Optional.ofNullable(sameDay).isPresent()) {
									name = name + "," + sameDay.getString("name");
									sameDay.put("name", name);
									continue;
								}

								//节日详情
								JSONArray daylist = holidayInfo.getJSONArray("list");
								for (Object infoobj : daylist) {
									JSONObject holiday = (JSONObject) infoobj;
									JSONObject info = new JSONObject();
									String date = holiday.getString("date");
									if (festival.equals(date)) {
										info.put("name", name);
									}
									//status=1表示放假，status=2表示上班（周末调休的上班）
									int type = holiday.getInteger("status");
									switch (type) {
										case 1:
											type = 2;//type 工作日0,节假日1,休息日2,
											break;
										case 2:
											type = 0;
											break;
									}
									info.put("type", type);
									holidayMap.put(date, info);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return holidayMap;
	}
//
//	/**
//	 * 生成工作日历
//	 * @param year 年份
//	 * @param month 月份
//	 * @return
//	 */
//	public static EntityBean createWorkCalendar(String year, String month) {
//		String year_month = year + "年" + month + "月";
//		String param = HOLIDAY_PARAM.replace("YEAR_MONTH",year_month);
//		EntityBean result = HttpUtil.sendGet(BAIDU_URL, param);
//		return result;
//	}
//
//	 /**
//	 * 获取对应时间附近的假日信息
//	 * @param year_month 2019年9月
//	 * @return
//	 */
//	public void queryHolidays(String year_month){
//		String param = HOLIDAY_PARAM.replace("YEAR_MONTH",year_month);
//	    try {
//	    	EntityBean result = HttpUtil.sendGet(BAIDU_URL, param);
//	    	if (Optional.ofNullable(result).isPresent()) {
//	    		int status = result.getInt("status");
//	    		if (status == 0) {
//	    			EntityBean[] monthData = result.getBeans("data");
//	    	    	if (Optional.ofNullable(monthData).isPresent()) {
//	    	    		for (EntityBean data : monthData) {
//	    	    			//日期信息
//	    	    			EntityBean[] almanac = data.getBeans("almanac");
//	    	    			//holidaylist字段，节假日信息[{"name": "中秋节","startday": "2020-10-1"}],name节假日名称，startday开始日期
//	    	    			EntityBean[] holidaylist = data.getBeans("holidaylist");
//	    	    			//holiday字段，节假日信息详细信息，rest解释节假日，desc描述调班信息，status=1表示放假，status=2表示上班（周末调休的上班）
//	    	    			EntityBean[] holidays = data.getBeans("holiday");
//
//	    	    			Map<String, Object> dayInfos = handleDays(almanac, holidays);
//	    	    			System.out.println(JSONSerializer.getInstance().Serialize(dayInfos));
//	    	    		}
//	    	    	}
//	    		}
//	    	}
//	    }catch (Exception ex){
//	        ex.printStackTrace();
//	    }
//	}
//
//	/**
//	 * 处理节假日信息
//	 * @param days 日期信息
//	 * @param holiday 节假日信息详细信息，rest解释节假日，desc描述调班信息，status=1表示放假，status=2表示上班（周末调休的上班）
//	 * @param holidaylist 节假日信息,name节假日名称，startday开始日期
//	 * @return
//	 */
//	public Map<String, Object> handleDays(EntityBean[] days, EntityBean[] holidays) {
//		Map<String, Object> dayMap = new HashMap<String, Object>();
//		try {
//			//节假日信息
//			Map<String, Object> holidayMap = new HashMap<String, Object>();
//			for (EntityBean holidayInfo : holidays) {
//				//{"desc": "1月1日放假一天","festival": "2020-1-1","list": [{"date": "2020-1-1", "status": "1"}],"list#num#baidu": 1,"name": "元旦","rest": "2019年12月30日和2019年12月31日请假两天，与周末连休可拼5天小长假。"}
//				String name = holidayInfo.getString("name");//节日名称
//				String festival = holidayInfo.getString("festival");//节日开始日期
//
//				EntityBean[] daylist = holidayInfo.getBeans("list");//节日放假日期
//				for (EntityBean holiday : daylist) {
//					EntityBean info = new EntityBean();
//					String date = holiday.getString("date");
//					if(festival.equals(date)) {
//						info.set("name", name);
//					}
//					int status = holiday.getInt("status");//status=1表示放假，status=2表示上班（周末调休的上班）
//					info.set("status", status);
//					holidayMap.put(date, info);
//				}
//			}
//
//			//处理日期信息
//			for (EntityBean day : days) {
//				EntityBean dayInfo = new EntityBean();
//				//{"avoid": "诸事不宜.","date": "2020-10-3","suit": "破屋.坏垣.余事勿取."}
//				String date = day.getString("date");
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(sdf_ymd.parse(date));
//				int type = 0;//type 0工作日，1非工作日
//				int week = cal.get(Calendar.DAY_OF_WEEK);
//				if(week == 1 || week == 7) {
//					type = 1;
//				}
//				//节假日信息
//				EntityBean holiday = (EntityBean) holidayMap.get(date);
//				if (Optional.ofNullable(holiday).isPresent()) {
//					String name = holiday.getString("name");//节假日名称
//					if(StringUtils.isNotEmpty(name)) {
//						dayInfo.set("name", name);
//					}
//					int status = holiday.getInt("status");//status=1表示放假，status=2表示上班（周末调休的上班）
//					if(1 == status) {
//						type = 1;
//					}
//				}
//				dayInfo.set("type", type);
//				dayMap.put(date, dayInfo);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return dayMap;
//	}
	

}
