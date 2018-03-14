package com.epicc.general;

import com.epicc.annotation.ExcelColName;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 抽取公共参数的实体类
*/
public class CommonModel {
	@ExcelColName("预计结果")
	protected	String expect;
	
	@ExcelColName("险种编号")
	protected String productcode;
	
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
}
