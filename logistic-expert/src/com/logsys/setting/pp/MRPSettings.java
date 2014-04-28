package com.logsys.setting.pp;


/**
 * MRP设置接口
 * @author ShaonanLi
 */
public interface MRPSettings {

	/**
	 * 由生产线获取报废率
	 * @param fertpn 成品号
	 * @return 报废率
	 */
	public float getScarpRateByPN(String fertpn);
	
	/**
	 * 获取向上取整数值
	 * @param fertpn 成品号
	 * @return 向上取整值
	 */
	public int getCeilingValueByPN(String pn);
	
}
