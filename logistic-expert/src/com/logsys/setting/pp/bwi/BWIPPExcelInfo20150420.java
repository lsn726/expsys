package com.logsys.setting.pp.bwi;

import java.util.Calendar;
import java.util.Date;

/**
 * BWI�����ƻ�Excel��Ϣ�汾:2014��4��10��
 * @author ShaonanLi
 */
public class BWIPPExcelInfo20150420 implements BWIPPExcelInfo {

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
		return 27;
	}

	@Override
	public int getFA2DateRow() {
		return 32;
	}

	@Override
	public int getFA2PPBeginRow() {
		return 34;
	}

	@Override
	public int getFA2PPEndRow() {
		return 66;
	}

	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, Calendar.JULY, 15);
		return cal.getTime();
	}

}