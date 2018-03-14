package com.epicc.general;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
* @author 刘飞
* @version 创建时间：2018年3月14日 下午5:59:49
* @Description 类描述
*/
@Component
public class PackageIdMapModel {
	public static Map<String, String> packageIdMap = new HashMap<String, String>();
	static {
		packageIdMap.put("JBM", "JBM0001352");
		packageIdMap.put("EAU_DY", "EAU0000001");
	}
}
