package com.sinosoft.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
* @author ����
* @version ����ʱ�䣺2018��3��8�� ����5:57:40
* @Description ������
*/
public class DateUtils {
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
