package com.logsys.setting.pp.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * BWI生产计划Excel信息版本:2014年4月10日
 * @author ShaonanLi
 */
public class BWIPPExcelInfo20161013 implements BWIPPExcelInfo {

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
		return 80;
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2016, Calendar.OCTOBER, 13);
		return cal.getTime();
	}

	@Override
	public int getHondaModuleBeginRow() {
		return 91;
	}

	@Override
	public int getHondaModuleEndRow() {
		// TODO Auto-generated method stub
		return 96;
	}

}