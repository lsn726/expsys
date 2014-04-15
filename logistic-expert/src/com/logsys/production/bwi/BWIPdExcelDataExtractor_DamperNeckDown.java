package com.logsys.production.bwi;

/**
 * Damper Neck Down数据提取器
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperNeckDown extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperNeckDown() {
		super();
		adjustValidatorStrMap(ValidatorStr.Interval_Early1, "8:15-9:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early2, "9:00-10:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early3, "10:00-11:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early4, "11:00-12:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early5, "12:00-13:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early6, "13:00-14:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early7, "14:00-15:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early8, "15:00-16:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Early9, "16:00-16:45");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle1, "16:45-17:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle2, "17:00-18:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle3, "18:00-19:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle4, "19:00-20:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle5, "20:00-21:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle6, "21:00-22:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle7, "22:00-23:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle8, "23:00-24:00");
		adjustValidatorStrMap(ValidatorStr.Interval_Middle9, "00:00-01:15");
		refreshLocStrValidator();
	}
	
}
