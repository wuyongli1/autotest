package com.epicc.notcar;

import com.epicc.annotation.ExcelColName;
import com.epicc.annotation.ExcelSheetName;
import com.epicc.general.CommonModel;
import com.epicc.general.PackageIdMapModel;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 老非车险API接口实体类
*/
@ExcelSheetName("老非车险接口")
public class EAU_DY extends CommonModel{
	@ExcelColName("城市编号")
	private String citycode;
	
	@ExcelColName("人数")
	private String peoplecount;
	
	@ExcelColName("购买份数")
	private String copies;
	
	@ExcelColName("起保日期")
	private String startDate;
	
	@ExcelColName("终保日期")
	private String endDate;
	
	@ExcelColName("packageid")
	private String packageId;
	
//	private String packageInfoList;

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getPeoplecount() {
		return peoplecount;
	}

	public void setPeoplecount(String peoplecount) {
		this.peoplecount = peoplecount;
	}

	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPackageInfoList() {
		return "[{\"packageid\":\""+packageId+"\",\"packagename\":\"\",\"packclass\":\"\",\"peoplecount\":\""+peoplecount+"\",\"riskcode\":\""+productcode+"\"}]";
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
}
