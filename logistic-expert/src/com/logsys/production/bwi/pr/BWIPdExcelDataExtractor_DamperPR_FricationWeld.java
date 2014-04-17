package com.logsys.production.bwi.pr;

/**
 * BWI生产Excel信息提取器--减震器PR提取器--摩擦焊
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_FricationWeld extends BWIPdExcelDataExtractor_DamperPR {

	public BWIPdExcelDataExtractor_DamperPR_FricationWeld() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Early9, "16：00-16450");
		validatorStrMap.put(ValidatorStr.Interval_Middle2, "18:00-19:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle3, "19:00-20:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "20:00-21:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle5, "21:00-22:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle7, "23：00-00：00");
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0:00-01:15");
		initLocValidatorStrMap();
	}
	
}
