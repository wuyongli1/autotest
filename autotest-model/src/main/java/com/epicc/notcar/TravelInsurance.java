package com.epicc.notcar;

import com.epicc.annotation.ExcelColName;
import com.epicc.annotation.ExcelSheetName;

/**
* @author 刘飞
* @version 创建时间：2018年3月8日 下午5:46:38
* @Description 旅游险实体类
*/
@ExcelSheetName("全球旅游保险")
public class TravelInsurance {
	@ExcelColName(value="cooperId")
	private String cooperId;
	@ExcelColName(value="出访目的地")
	private String businessSite;
	@ExcelColName(value="保险类型编号")
	private String riskCode;
	@ExcelColName(value="方案编号")
	private String rationType;
	@ExcelColName(value="startDate")
	private String startDate;
	@ExcelColName(value="endDate")
	private String endDate;
	@ExcelColName(value="被保险人数")
	private String personnes;
	@ExcelColName(value="entryId")
	private String entryId;
	@ExcelColName(value="sessionId")
	private String sessionId;
	public String getCooperId() {
		return cooperId;
	}
	public void setCooperId(String cooperId) {
		this.cooperId = cooperId;
	}
	public String getBusinessSite() {
		return businessSite;
	}
	public void setBusinessSite(String businessSite) {
		this.businessSite = businessSite;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getRationType() {
		return rationType;
	}
	public void setRationType(String rationType) {
		this.rationType = rationType;
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
	public String getPersonnes() {
		return personnes;
	}
	public void setPersonnes(String personnes) {
		this.personnes = personnes;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "TravelInsurance [cooperId=" + cooperId + ", businessSite=" + businessSite + ", riskCode=" + riskCode
				+ ", rationType=" + rationType + ", startDate=" + startDate + ", endDate=" + endDate + ", personnes="
				+ personnes + ", entryId=" + entryId + ", sessionId=" + sessionId + "]";
	}
}
