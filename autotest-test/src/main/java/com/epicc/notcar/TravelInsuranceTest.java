package com.epicc.notcar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.epicc.general.AssertResult;
import com.epicc.general.ExcelService;
import com.epicc.utils.ExcelUtil;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 类描述
*/
@Test(groups={"oldInterface"})
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TravelInsuranceTest extends AbstractTestNGSpringContextTests{
	String path = "C:\\Users\\Administrator\\Desktop\\epicc.xls";
	private List<TravelInsurance> travelInsuranceList = null;
	private List<AssertResult> assertResultList = new ArrayList<AssertResult>();
	
	@Autowired
	private TravelInsuranceService tis;
	@Autowired
	private ExcelService es;
	
	//将excel表格数据映射为实体对象
	@BeforeTest
	public void initModel() {
//		String path = PriceTest.class.getClassLoader().getResource("excel/epicc.xls").getPath();
		try {
			TravelInsuranceVo travelInsuranceVo = ExcelUtil.setExcelInfo2Bean(new File(path),new TravelInsuranceVo());
			travelInsuranceList = travelInsuranceVo.getTravelInsuranceList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//将比较结果回写到excel表中
	@AfterTest
	public void addResult() throws Exception {
		es.writeResult(assertResultList,new TravelInsurance(),new File(path));
	}
	
	//测试接口
	@Test
	public void price() throws InterruptedException {
		for (TravelInsurance travelInsurance : travelInsuranceList) {
			AssertResult assertResult = tis.price(travelInsurance);
			Thread.sleep(500);
			assertResultList.add(assertResult);
		}
	}
}