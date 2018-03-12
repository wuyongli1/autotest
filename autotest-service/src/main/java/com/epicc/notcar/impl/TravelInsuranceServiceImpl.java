package com.epicc.notcar.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
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

import com.epicc.notcar.TravelInsurance;
import com.epicc.notcar.TravelInsuranceService;
import com.epicc.utils.DateUtils;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午3:07:22
* @Description 旅游险service实现类
*/
@Service
public class TravelInsuranceServiceImpl implements TravelInsuranceService{
	public void price(TravelInsurance travelInsurance) {
		String tomorrow = DateUtils.getDateFormat(new Date());
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://www.epicc.com.cn/wap/proposal/fee/price");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
			httpPost.setHeader("Host", "www.epicc.com.cn");
			httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			httpPost.setHeader("User-Agent", "Keep-Alive");
			httpPost.setHeader("Referer", "http://www.epicc.com.cn/wap/views/proposal/E/EAJ/EAJProposal.jsp");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
			//断言 sumPremiums 160.00
			//设置参数
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			Header[] allHeaders = response.getAllHeaders();
			HttpEntity entity = response.getEntity();
			for (Header header : allHeaders) {
				System.err.println(header);
			}
			if (entity != null) {
				String string = EntityUtils.toString(entity, "utf-8");
				System.err.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
