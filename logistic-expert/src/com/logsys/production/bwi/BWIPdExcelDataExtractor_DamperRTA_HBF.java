package com.logsys.production.bwi;

/**
 * Damper Hot Bottom Forming 0031/0835的数据提取器
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperRTA_HBF extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperRTA_HBF() {
		super();
		adjustValidatorStrMap(ValidatorStr.Interval_Middle4, "19：00-20:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle5, "20：00-21:00");
		refreshLocStrValidator();
	}
	
}
