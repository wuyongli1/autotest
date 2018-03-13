package com.epicc.notcar;

import java.io.File;
import java.util.List;

import com.epicc.general.AssertResult;
import com.epicc.general.AssertResultVo;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午3:07:22
* @Description 旅游险service接口
*/
public interface TravelInsuranceService {
	AssertResult price(TravelInsurance travelInsurance);
	void setResult(List<AssertResult> assertResultList, List<TravelInsurance> travelInsuranceList, File file) throws Exception;
}
