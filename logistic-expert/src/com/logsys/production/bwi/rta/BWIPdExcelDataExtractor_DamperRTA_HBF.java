package com.logsys.production.bwi.rta;


/**
 * Damper Hot Bottom Forming 0031/0835��������ȡ��
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperRTA_HBF extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperRTA_HBF() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "19��00-20:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle5, "20��00-21:00");
		initLocValidatorStrMap();
	}
	
}
