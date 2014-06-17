package com.logsys.setting.pp.bwi;

import java.util.HashMap;
import java.util.Map;

import com.logsys.setting.pp.MRPSettings;

/**
 * MRP设定集
 * @author ShaonanLi
 */
public class BWIMRPSettings implements MRPSettings {
	
	private static Map<String,Float> scarpMap=new HashMap<String,Float>();

	private static Map<String,Integer> ceilMap=new HashMap<String,Integer>();
	
	static {
		//报废率图
		scarpMap.put("22271370", 0.03f);
		scarpMap.put("22271371", 0.03f);
		scarpMap.put("22271372", 0.03f);
		scarpMap.put("22271373", 0.03f);
		scarpMap.put("22271375", 0.03f);
		scarpMap.put("22271376", 0.03f);
		scarpMap.put("22261400", 0.05f);
		scarpMap.put("22261420", 0.03f);
		scarpMap.put("22268411", 0.03f);
		scarpMap.put("22272171", 0.03f);
		scarpMap.put("22272172", 0.03f);
		scarpMap.put("22272138", 0.05f);
		scarpMap.put("22291991", 0.05f);
		scarpMap.put("22265577", 0.05f);
		scarpMap.put("22265578", 0.05f);
		scarpMap.put("22265579", 0.05f);
		scarpMap.put("22265580", 0.05f);
		scarpMap.put("22265581", 0.05f);
		scarpMap.put("22265582", 0.05f);
		scarpMap.put("22272176", 0.05f);
		scarpMap.put("22272177", 0.05f);
		scarpMap.put("22271789", 0.05f);
		scarpMap.put("22292191", 0.05f);
		scarpMap.put("22292218", 0.10f);
		scarpMap.put("22292219", 0.10f);
		scarpMap.put("22292220", 0.10f);
		scarpMap.put("22280909", 0.10f);
		scarpMap.put("22280908", 0.10f);
		scarpMap.put("22283595", 0.10f);
		scarpMap.put("22283596", 0.10f);
		scarpMap.put("22284696", 0.03f);
		scarpMap.put("22284434", 0.03f);
		//向上取整图
		ceilMap.put("22271370", 50);
		ceilMap.put("22271371", 50);
		ceilMap.put("22271372", 50);
		ceilMap.put("22271373", 50);
		ceilMap.put("22271375", 50);
		ceilMap.put("22271376", 50);
		ceilMap.put("22261400", 50);
		ceilMap.put("22261420", 50);
		ceilMap.put("22268411", 50);
		ceilMap.put("22272171", 50);
		ceilMap.put("22272172", 50);
		ceilMap.put("22272138", 50);
		ceilMap.put("22292191", 50);
		ceilMap.put("22265577", 20);
		ceilMap.put("22265578", 20);
		ceilMap.put("22265579", 20);
		ceilMap.put("22265580", 20);
		ceilMap.put("22265581", 20);
		ceilMap.put("22265582", 20);
		ceilMap.put("22272176", 20);
		ceilMap.put("22272177", 20);
		ceilMap.put("22271789", 20);
		ceilMap.put("22292191", 20);
		ceilMap.put("22292218", 20);
		ceilMap.put("22292219", 20);
		ceilMap.put("22292220", 20);
		ceilMap.put("22280909", 20);
		ceilMap.put("22280908", 20);
		ceilMap.put("22283595", 20);
		ceilMap.put("22283596", 20);
		ceilMap.put("22284696", 20);
		ceilMap.put("22284434", 20);
	}

	@Override
	public Float getScarpRateByPN(String fertpn) {
		return scarpMap.get(fertpn);
	}

	@Override
	public int getCeilingValueByPN(String fertpn) {
		return ceilMap.get(fertpn);
	}

}
