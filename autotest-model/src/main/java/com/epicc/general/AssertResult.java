package com.epicc.general;

import com.epicc.annotation.ExcelColName;

/**
* @author 刘飞
* @version 创建时间：2018年3月13日 下午2:53:50
* @Description 类描述
*/
public class AssertResult {
	
	@ExcelColName("实际结果")
	private String actual;
	
	@ExcelColName("断言")
	private Boolean result;
	
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Result [actual=" + actual + ", result=" + result + "]";
	}
}
