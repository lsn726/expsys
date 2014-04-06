package com.logsys.setting.pp.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * BWI生产计划Excel信息版本:2014年3月31日
 * @author ShaonanLi
 */
public class BWIPPExcelInfo20140331 implements BWIPPExcelInfo {

	@Override
	public int getPnCol() {
		return 0;
	}

	@Override
	public int getFA1DateRow() {
		return 3;
	}

	@Override
	public int getFA1PPBeginRow() {
		return 4;
	}

	@Override
	public int getFA1PPEndRow() {
		return 23;
	}

	@Override
	public int getFA2DateRow() {
		return 25;
	}

	@Override
	public int getFA2PPBeginRow() {
		return 27;
	}

	@Override
	public int getFA2PPEndRow() {
		return 44;
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, cal.MARCH, 31);
		return cal.getTime();
	}

}