package com.logsys.setting.pp.bwi;

import java.util.HashMap;
import java.util.Map;

import com.logsys.setting.pp.MRPSettings;

/**
 * MRP…Ë∂®ºØ
 * @author ShaonanLi
 */
public class BWIMRPSettings implements MRPSettings {
	
	private static Map<String,Float> scarpMap=new HashMap<String,Float>();

	private static Map<String,Integer> ceilMap=new HashMap<String,Integer>();
	
	static {
		//scarpMap.put("", arg1)
		//ceilMap.put("", value)
	}
	
	@Override
	public float getScarpRateByPN(String fertpn) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCeilingValueByPN(String pn) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
