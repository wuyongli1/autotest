package com.epicc.utils;
/**
* @author 刘飞
* @version 创建时间：2018年3月13日 下午4:15:57
* @Description 类描述
*/
public class CompareUtil {
	public static boolean compare(String expect,String actual) {
		Double actual_d = Double.parseDouble(actual);
		Double expect_d = Double.parseDouble(expect);
		Double compareResult = actual_d - expect_d;
		if(compareResult>=0.01||compareResult<=-0.01) {
			return false;
		}else {
			return true;
		}
	}
}
