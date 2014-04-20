package com.logsys.setting.pd.bwi;

import java.util.HashMap;
import java.util.Map;

import com.logsys.production.ProductionInterval;
import com.logsys.util.Location;

public abstract class BWIPdExcelInfo {

	/**由于HashMap不接受NULL或者“”为Value，因此只能采用这种方法标记空字符串。*/
	public static final String EMPTY_STR="EMPTR_STR_INDENTIFIER";
	
	/**配置Sheet名*/
	private static final String CONFIGURE_SHEETNAME="Configuration";
	
	/**零件号列相对于产出数量列的差*/
	public static final int RELCOL_PARTNUM_OUTPUTQTY=-2;
	
	/**操作工数量相对于产出数量列的差*/
	public static final int RELCOL_OPERQTY_OUTPUTQTY=-3;
	
	/**配置Sheet中，生产线名称所在列*/
	public static final Location PLLOC=new Location(1,0);
	
	/**验证映射图：位置->验证字符串*/
	protected static final Map<Location,String> VERIFYMAP_LOC_STR=new HashMap<Location,String>();
	
	/**产品映射图：别名->产品标准Part Number*/
	protected static final Map<String,String> PRODMAP_ALIAS_STDPN=new HashMap<String,String>();
	
	/**生产区间映射图：位置->生产区间对象，此映射图包含了所有可能记录产出数据的位置*/
	protected static final Map<Location,ProductionInterval> PRODMAP_LOC_INTERVAL=new HashMap<Location,ProductionInterval>();
	
	public BWIPdExcelInfo() {
		initLocVerifyStrMap();		//初始化映射表:位置->验证字符串
		initProdAliasStdPnMap();	//初始化映射表:别名->标准名
		initProdInterval();			//初始化映射表:生产区间枚举->生产区间对象
	}
	
	/**
	 * 初始化映射图：位置->验证字符串
	 */
	protected abstract void initLocVerifyStrMap();
	
	/**
	 * 初始化映射图：别名->产品标准Part Number
	 */
	protected abstract void initProdAliasStdPnMap();
	
	/**
	 * 初始化映射图：生产区间枚举->生产区间对象
	 */
	protected abstract void initProdInterval();
	
	/**
	 * 获取配置Sheet名称
	 * @return 配置Sheet名称
	 */
	public static String getConfigSheetName() {
		return CONFIGURE_SHEETNAME;
	}
	
	/**
	 * 获取生产日期位置
	 * @return 生产日期位置
	 */
	public abstract Location getDateLocation();
	
	/**
	 * 获取验证字符串映射图
	 * @return 验证映射图
	 */
	public Map<Location,String> getLocVerifyStrMap() {
		return VERIFYMAP_LOC_STR;
	}
	
	/**
	 * 获取产品别名->标准名映射图
	 * @return 验证映射图
	 */
	public Map<String,String> getProdAliasStdPnMap() {
		return PRODMAP_ALIAS_STDPN;
	}
	
	/**
	 * 获取位置->生产区间映射表
	 * @return 位置->生产区间映射表
	 */
	public Map<Location,ProductionInterval> getLocIntervalMap() {
		return PRODMAP_LOC_INTERVAL;
	}
	
}
