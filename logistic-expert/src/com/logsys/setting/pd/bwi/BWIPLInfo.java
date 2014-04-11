package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI生产线信息抽象类
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {

	/**
	 * 获取总装1线名称
	 * @return 总装1线名称
	 */
	public abstract String getFA1Name();
	
	/**
	 * 获取总装2线名称
	 * @return 总装2线名称
	 */
	public abstract String getFA2Name();
	
	/**
	 * 通过别名获取标准生产线名称
	 * @param alias 别名
	 * @return 生产线名称/失败null
	 */
	public abstract String getStdProdlineNameByAlias(String alias);
	
	/**
	 * 返回信息类版本
	 * @return 版本
	 */
	public abstract Date getVersion();
	
}
