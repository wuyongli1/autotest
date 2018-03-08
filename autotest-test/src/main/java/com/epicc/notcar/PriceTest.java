package com.epicc.notcar;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.sinosoft.utils.ExcelUtil;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 类描述
*/
@Test
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PriceTest {
	Logger logger = Logger.getLogger(PriceTest.class);
	private TravelInsurance travelInsurance;
	
	@Autowired
	private TravelInsuranceService tis;
	
	//将excel表格数据初始化为对象
	@BeforeTest
	public void initModel() {
		try {
			ExcelUtil.initModel(travelInsurance);
		} catch (FileNotFoundException e) {
			logger.error("excel文件路径错误！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void price() {
		tis.price(travelInsurance);
	}
}
