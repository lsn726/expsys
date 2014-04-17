package com.logsys.production.bwi.pr;


/**
 * BWI生产Excel信息提取器--减震器PR提取器--渡后超精
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_SupFin extends BWIPdExcelDataExtractor_DamperPR {

	public BWIPdExcelDataExtractor_DamperPR_SupFin() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Early2, "9:00-10:00");
		validatorStrMap.put(ValidatorStr.Interval_Early3, "10:00-11:00");
		validatorStrMap.put(ValidatorStr.Interval_Early4, "11:00-12:00");
		validatorStrMap.put(ValidatorStr.Interval_Early5, "12:00-13：00");
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0：00-01：15");
		initLocValidatorStrMap();
	}
	
}
