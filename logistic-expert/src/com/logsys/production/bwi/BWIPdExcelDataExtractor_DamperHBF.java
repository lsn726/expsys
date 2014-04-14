package com.logsys.production.bwi;

import com.logsys.production.bwi.BWIPdExcelDataExtractor_DamperRTA.ValidatorStr;

/**
 * Damper Hot Bottom Forming 0031/0835的数据提取器
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperHBF extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperHBF() {
		super();
	}
	
	/**
	 * 使用自己的验证字符串枚举->验证字符串表
	 */
	protected void initValidatorStr() {
		validatorStrMap.clear();
		validatorStrMap.put(ValidatorStr.Interval_0815_0900, "8：15-9：00");
		validatorStrMap.put(ValidatorStr.Interval_0900_1000, "9：00-10：00");
		validatorStrMap.put(ValidatorStr.Interval_1000_1100, "10：00-11：00");
		validatorStrMap.put(ValidatorStr.Interval_1100_1200, "11：00-12：00");
		validatorStrMap.put(ValidatorStr.Interval_1200_1300, "12：00-13：00");
		validatorStrMap.put(ValidatorStr.Interval_1300_1400, "13：00-14：00");
		validatorStrMap.put(ValidatorStr.Interval_1400_1500, "14：00-15：00");
		validatorStrMap.put(ValidatorStr.Interval_1500_1600, "15：00-16：00");
		validatorStrMap.put(ValidatorStr.Interval_1600_1645, "16：00-16：45");
		validatorStrMap.put(ValidatorStr.Interval_1645_1700, "16：45-17：00");
		validatorStrMap.put(ValidatorStr.Interval_1700_1800, "17：00-18：00");
		validatorStrMap.put(ValidatorStr.Interval_1800_1900, "18：00-19：00");
		validatorStrMap.put(ValidatorStr.Interval_1900_2000, "19：00-20:00");
		validatorStrMap.put(ValidatorStr.Interval_2000_2100, "20：00-21:00");
		validatorStrMap.put(ValidatorStr.Interval_2100_2200, "21：00-22：00");
		validatorStrMap.put(ValidatorStr.Interval_2200_2300, "22：00-23：00");
		validatorStrMap.put(ValidatorStr.Interval_2300_2400, "23：00-24：00");
		validatorStrMap.put(ValidatorStr.Interval_2400_2515, "00：00-01：15");
	}
	
}
