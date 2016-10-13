package com.logsys.setting.pp.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * BWI生产计划Excel信息版本:2014年4月10日
 * @author ShaonanLi
 */
public class BWIPPExcelInfo20150515 implements BWIPPExcelInfo {

	@Override
	public int getPnCol() {
		return 0;
	}

	@Override
	public int getFA1DateRow() {
		return 4;
	}

	@Override
	public int getFA1PPBeginRow() {
		return 5;
	}

	@Override
	public int getFA1PPEndRow() {
		return 29;
	}

	@Override
	public int getFA2DateRow() {
		return 0;
	}

	@Override
	public int getFA2PPBeginRow() {
		return 37;
	}

	@Override
	public int getFA2PPEndRow() {
		return 84;
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2015, Calendar.MAY, 15);
		return cal.getTime();
	}

}