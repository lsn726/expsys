package com.logsys.production.bwi.pr;


/**
 * BWI生产Excel信息提取器--减震器PR提取器--CNC线
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_CNC extends BWIPdExcelDataExtractor_DamperPR {
	
	public BWIPdExcelDataExtractor_DamperPR_CNC() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Early1, "8：15-9:00");
		validatorStrMap.put(ValidatorStr.Interval_Early2, "9:00-10:00");
		validatorStrMap.put(ValidatorStr.Interval_Early3, "10:00-11:00");
		validatorStrMap.put(ValidatorStr.Interval_Early4, "11:00-12:00");
		validatorStrMap.put(ValidatorStr.Interval_Early5, "12:00-13:00");
		validatorStrMap.put(ValidatorStr.Interval_Early6, "13:00-14:00");
		validatorStrMap.put(ValidatorStr.Interval_Early8, "15:00-16:00");
		validatorStrMap.put(ValidatorStr.Interval_Early9, "16:00-16：45");
		validatorStrMap.put(ValidatorStr.Interval_Middle2, "18:00-19:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle3, "19:00-20:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "20:00-21:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle5, "21:00-22:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle6, "22:00-23:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle7, "23:00-0:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0:00-01：15");
		initLocValidatorStrMap();
	}

}
