package com.logsys.setting.pd.bwi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产线信息版本20140406
 * @author ShaonanLi
 *
 */
public class BWIPLInfo20140331 extends BWIPLInfo {

	/**Map<别名,生产线>图*/
	private static Map<String,String> aliasmap=new HashMap<String,String>();

	{
		aliasmap.put("UV", "UV");
		aliasmap.put("UV生产线/UV", "UV");
		aliasmap.put("Final Assembly 1","Final Assembly 1");
		aliasmap.put("Final Assembly 2","Final Assembly 2");
		aliasmap.put("FA1", "Final Assembly 1");
		aliasmap.put("FA2", "Final Assembly 2");
	}
	
	@Override
	public String getFA1Name() {
		return "Final Assembly 1";
	}

	@Override
	public String getFA2Name() {
		return "Final Assembly 2";
	}

	@Override
	public String getStdProdlineNameByAlias(String alias) {
		if(aliasmap.containsKey(alias)) return aliasmap.get(alias);
		return null;
	}
	
	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, Calendar.MARCH, 31);
		return cal.getTime();
	}
	
}
