package com.logsys.setting.pd.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * 生产线信息版本20140406
 * @author ShaonanLi
 *
 */
public class BWIPLInfo20140331 extends BWIPLInfo {

	@Override
	public String getFA1Name() {
		return "Final Assembly 1";
	}

	@Override
	public String getFA2Name() {
		return "Final Assembly 2";
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, Calendar.MARCH, 31);
		return cal.getTime();
	}
	
}
