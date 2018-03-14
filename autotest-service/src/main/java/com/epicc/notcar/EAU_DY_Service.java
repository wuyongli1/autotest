package com.epicc.notcar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.testng.log4testng.Logger;
import com.epicc.general.AssertResult;
import com.epicc.notcar.EAU_DY;
import com.epicc.notcar.TravelInsurance;
import com.epicc.utils.CompareUtil;
import com.epicc.utils.DateUtil;
import net.sf.json.JSONObject;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午3:07:22
* @Description 不记名驾乘意外险实现类
*/
@Service
public class EAU_DY_Service{
	Logger logger = Logger.getLogger(EAU_DY_Service.class);
	public AssertResult price(EAU_DY eau_dy) {
		String tomorrow = DateUtil.getDateFormat(new Date(),"yyyy/MM/dd");
		AssertResult assertResult = new AssertResult();
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://www.epicc.com.cn/wap/proposal/configFee/price");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
			httpPost.setHeader("Host", "www.epicc.com.cn");
			httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			httpPost.setHeader("Connection", "Keep-Alive");
			httpPost.setHeader("Referer", "http://www.epicc.com.cn/wap/views/dataDictionaryConfighoutai/index.jsp?productcode=EAU_DY");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			//设置post提交参数
			NameValuePair citycode = new BasicNameValuePair("citycode", eau_dy.getCitycode());
			NameValuePair productcode = new BasicNameValuePair("productcode", eau_dy.getProductcode());
			NameValuePair packageInfoList = new BasicNameValuePair("packageInfoList", eau_dy.getPackageInfoList());
			NameValuePair Copies = new BasicNameValuePair("Copies", eau_dy.getCopies());
			NameValuePair startDate = new BasicNameValuePair("startDate", eau_dy.getStartDate());
			NameValuePair endDate = new BasicNameValuePair("endDate", eau_dy.getEndDate());
			NameValuePair packageid = new BasicNameValuePair("packageid", eau_dy.getPackageId());
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(citycode);
			list.add(productcode);
			list.add(packageInfoList);
			list.add(Copies);
			list.add(startDate);
			list.add(endDate);
			list.add(packageid);
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String json = EntityUtils.toString(entity, "utf-8");
				JSONObject jo = JSONObject.fromObject(json);
				String sumpremium = (String) jo.get("sumpremium");
				System.err.println("sumPremiums="+sumpremium);
				assertResult.setActual(sumpremium);
				assertResult.setResult(CompareUtil.compare(eau_dy.getExpect(),sumpremium));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assertResult;
	}
}
