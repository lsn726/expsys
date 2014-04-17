package com.logsys.setting.pp.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * BWI生产计划Excel信息版本:2014年4月10日
 * @author ShaonanLi
 */
public class BWIPPExcelInfo20140410 implements BWIPPExcelInfo {

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
		return 26;
	}

	@Override
	public int getFA2DateRow() {
		return 28;
	}

	@Override
	public int getFA2PPBeginRow() {
		return 30;
	}

	@Override
	public int getFA2PPEndRow() {
		return 50;
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, Calendar.APRIL, 10);
		return cal.getTime();
	}

}