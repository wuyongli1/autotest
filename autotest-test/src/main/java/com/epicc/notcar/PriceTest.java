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
* @author ����
* @version ����ʱ�䣺2018��3��8�� ����5:46:38
* @Description ������
*/
@Test
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PriceTest {
	Logger logger = Logger.getLogger(PriceTest.class);
	private TravelInsurance travelInsurance;
	
	@Autowired
	private TravelInsuranceService tis;
	
	//��excel������ݳ�ʼ��Ϊ����
	@BeforeTest
	public void initModel() {
		try {
			ExcelUtil.initModel(travelInsurance);
		} catch (FileNotFoundException e) {
			logger.error("excel�ļ�·������");
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
