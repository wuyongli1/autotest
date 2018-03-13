package com.epicc.notcar.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.testng.log4testng.Logger;

import com.epicc.annotation.ExcelColName;
import com.epicc.annotation.ExcelSheetName;
import com.epicc.general.AssertResult;
import com.epicc.general.AssertResultVo;
import com.epicc.notcar.TravelInsurance;
import com.epicc.notcar.TravelInsuranceService;
import com.epicc.notcar.TravelInsuranceVo;
import com.epicc.utils.CompareUtil;
import com.epicc.utils.DateUtil;
import com.epicc.utils.ExcelUtil;

import net.sf.json.JSONObject;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午3:07:22
* @Description 旅游险service实现类
*/
@Service
public class TravelInsuranceServiceImpl implements TravelInsuranceService{
	Logger logger = Logger.getLogger(TravelInsuranceServiceImpl.class);
	public AssertResult price(TravelInsurance travelInsurance) {
		String tomorrow = DateUtil.getDateFormat(new Date());
		AssertResult assertResult = new AssertResult();
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://www.epicc.com.cn/wap/proposal/fee/price");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
			httpPost.setHeader("Host", "www.epicc.com.cn");
			httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			httpPost.setHeader("User-Agent", "Keep-Alive");
			httpPost.setHeader("Referer", "http://www.epicc.com.cn/wap/views/proposal/E/EAJ/EAJProposal.jsp");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			//设置post提交参数
			NameValuePair cooperId = new BasicNameValuePair("cooperId", travelInsurance.getCooperId());
			NameValuePair businessSite = new BasicNameValuePair("businessSite", travelInsurance.getBusinessSite());
			NameValuePair riskCode = new BasicNameValuePair("riskCode", travelInsurance.getRiskCode());
			NameValuePair rationType = new BasicNameValuePair("rationType", travelInsurance.getRationType());
			NameValuePair startDate = new BasicNameValuePair("startDate", tomorrow);
			NameValuePair endDate = new BasicNameValuePair("endDate", tomorrow);
			NameValuePair personnes = new BasicNameValuePair("personnes", travelInsurance.getPersonnes());
			NameValuePair entryId = new BasicNameValuePair("entryId", travelInsurance.getEntryId());
			NameValuePair sessionId = new BasicNameValuePair("sessionId", travelInsurance.getSessionId());
			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			list.add(cooperId);
			list.add(businessSite);
			list.add(riskCode);
			list.add(rationType);
			list.add(startDate);
			list.add(endDate);
			list.add(personnes);
//			list.add(entryId);
//			list.add(sessionId);
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String json = EntityUtils.toString(entity, "utf-8");
				JSONObject jo = JSONObject.fromObject(json);
				String sumPremiums = (String) jo.get("sumPremiums");
				System.err.println("sumPremiums="+sumPremiums);
				assertResult.setActual(sumPremiums);
				assertResult.setResult(CompareUtil.compare(travelInsurance.getExpect(),sumPremiums));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assertResult;
	}

	public void setResult(List<AssertResult> assertResultList, List<TravelInsurance> travelInsuranceList, File file) throws Exception {
		for (int i = 0;i<travelInsuranceList.size();i++) {
			//得到sheetName
			String sheetName = travelInsuranceList.get(i).getClass().getAnnotation(ExcelSheetName.class).value();
			// 获取文件后缀
			String fileExt = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			// 创建流
			InputStream input = new FileInputStream(file);
			// 创建Workbook
			Workbook wb = null;
			// 创建sheet
			Sheet sheet = null;
			// 根据后缀判断excel 2003 or 2007+
			if (fileExt.equals("xls")) {
				wb = (HSSFWorkbook) WorkbookFactory.create(input);
			} else {
				wb = new XSSFWorkbook(input);
			}
			// 获取表
			sheet = wb.getSheet(sheetName);
			Map<String, Integer> cellNameMap = ExcelUtil.getCellNameMap(sheet);
			// 获取行数
			int rowNum = sheet.getLastRowNum();
			String actual = assertResultList.get(i).getClass().getDeclaredField("actual").getAnnotation(ExcelColName.class).value();
			String result = assertResultList.get(i).getClass().getDeclaredField("result").getAnnotation(ExcelColName.class).value();
			// 从第二行开始读取,并设置进实例
			for (int j = 1; j <= rowNum; j++) {
				Row row = sheet.getRow(j);
				// 获取sheet类的属性对应的表中的列的cell对象
				Cell cell_actual = row.createCell(cellNameMap.get(actual));
				cell_actual.setCellValue(assertResultList.get(i).getActual());
				Cell cell_result = row.createCell(cellNameMap.get(result));
				cell_result.setCellValue(assertResultList.get(i).getResult());
				FileOutputStream os =  new FileOutputStream(file);
				wb.write(os);
				os.close();
			}
			wb.close();
			input.close();
		}
	}
}
