package com.epicc.notcar;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import com.epicc.utils.ExcelUtil;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 类描述
*/
@Test
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PriceTest extends AbstractTestNGSpringContextTests{
	Logger logger = Logger.getLogger(PriceTest.class);
	private List<TravelInsurance> travelInsuranceList = null;
	
	@Autowired
	private TravelInsuranceService tis;
	
	//将excel表格数据初始化为对象
	@BeforeTest
	public void initModel() {
		String path = PriceTest.class.getClassLoader().getResource("excel/epicc.xls").getPath();
		ExcelUtil eu = new ExcelUtil();
		try {
			TravelInsuranceVo travelInsuranceVo = eu.setExcelInfo2Bean(new File(path),new TravelInsuranceVo());
			travelInsuranceList = travelInsuranceVo.getTravelInsuranceList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void price() {
		for (TravelInsurance travelInsurance : travelInsuranceList) {
			tis.price(travelInsurance);
		}
	}
}