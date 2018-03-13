package com.epicc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 时间格式工具类
*/
public class DateUtil {
	public static String getDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		String dateFormat = sdf.format(date);
		return dateFormat;
	}
}
