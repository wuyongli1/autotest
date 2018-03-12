package com.epicc.notcar;

import java.util.List;

/**
* @author 刘飞
* @version 创建时间：2018年3月12日 下午4:48:48
* @Description 类描述
*/
public class TravelInsuranceVo {
	private List<TravelInsurance> TravelInsuranceList;

	public List<TravelInsurance> getTravelInsuranceList() {
		return TravelInsuranceList;
	}

	public void setTravelInsuranceList(List<TravelInsurance> travelInsuranceList) {
		TravelInsuranceList = travelInsuranceList;
	}

	@Override
	public String toString() {
		return "TravelInsuranceVo [TravelInsuranceList=" + TravelInsuranceList + "]";
	}
}
