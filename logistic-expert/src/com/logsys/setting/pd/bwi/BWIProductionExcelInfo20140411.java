package com.logsys.setting.pd.bwi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.logsys.util.Location;

/**
 * BWI的生产Excel信息类版本2014/4/11
 * @author ShaonanLi
 */
public class BWIProductionExcelInfo20140411 implements BWIProductionExcelInfo {
	
	@Override
	public String getConfigSheetName() {
		return "Configuration";
	}
	
	@Override
	public Location getPrdLineNameLocInConfigSheet() {
		Location loc=new Location();
		loc.column=0;
		loc.row=1;
		return loc;
	}
	
	@Override
	public Location getDateInfoLocInConfigSheet() {
		Location loc=new Location();
		loc.column=1;
		loc.row=1;
		return loc;
	}

	@Override
	public int getOperatorQtyColInProdSheet() {
		return 6;
	}

	@Override
	public int getPnColInProdSheet() {
		return 7;
	}

	@Override
	public int getOutputQtyColInProdSheet() {
		return 9;
	}

	@Override
	public int getTimeFrameBeginColInProdSheet() {
		return 1;
	}
	
	@Override
	public int getTimeFrameEndColInProdSheet() {
		return 3;
	}

	@Override
	public int getEarlyShiftStartColInProdSheet() {
		return 1;
	}

	@Override
	public int getMiddleShiftStartColInProdSheet() {
		return 28;
	}

	@Override
	public int getNightShiftStartColInProdSheet() {
		return 55;
	}
	
	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, cal.APRIL, 11);
		return cal.getTime();
	}

	@Override
	public int getProductionBeginRowInProdSheet() {
		return 11;
	}

}
