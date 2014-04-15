package com.logsys.production.bwi;

public class BWIPdExcelDataExtractor_DamperLeakTestWashing extends BWIPdExcelDataExtractor_DamperRTA {
	
	public BWIPdExcelDataExtractor_DamperLeakTestWashing() {
		super();
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle4, "19£º00-20:00");
		this.refreshLocStrValidator();
	}

}
