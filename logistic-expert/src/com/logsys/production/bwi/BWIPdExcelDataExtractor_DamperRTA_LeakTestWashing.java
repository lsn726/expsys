package com.logsys.production.bwi;

/**
 * Damper Leak Test Washing��©��ϴ������ȡ��
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing extends BWIPdExcelDataExtractor_DamperRTA {
	
	public BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing() {
		super();
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle4, "19��00-20:00");
		this.refreshLocStrValidator();
	}

}
