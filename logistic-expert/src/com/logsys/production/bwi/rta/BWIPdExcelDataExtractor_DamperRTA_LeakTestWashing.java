package com.logsys.production.bwi.rta;


/**
 * Damper Leak Test Washing侧漏清洗数据提取器
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing extends BWIPdExcelDataExtractor_DamperRTA {
	
	public BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "19：00-20:00");
		initLocValidatorStrMap();
	}

}
