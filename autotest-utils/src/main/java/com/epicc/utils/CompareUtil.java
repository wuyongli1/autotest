package com.epicc.utils;
/**
* @author 刘飞
* @version 创建时间：2018年3月13日 下午4:15:57
* @Description 类描述
*/
public class CompareUtil {
	public static boolean compare(String expect,String actual) {
		if(expect.equals(actual)) {
			return true;
		}else {
			return false;
		}
	}
}
