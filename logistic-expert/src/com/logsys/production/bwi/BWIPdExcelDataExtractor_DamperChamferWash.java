package com.logsys.production.bwi;


/**
 * Damper Chamfering and Washing倒角清洗数据提取器
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperChamferWash extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperChamferWash() {
		super();
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle1, "17：00-18：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle2, "18：00-19：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle3, "19：00-20：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle4, "20：00-21:00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle5, "21：00-22：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle6, "22：00-23：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle7, "23：00-00：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle8, "00：00-01：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Middle9, "01：00-01：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night1, "02：30-03：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night2, "03：30-04：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night3, "04：30-05：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night4, "05：30-06：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night5, "06：30-07：30");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night6, "07：30-08：00");
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night7, EMPTYSTRVALUE);
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night8, EMPTYSTRVALUE);
		this.adjustValidatorStrMap(ValidatorStr.Interval_Night9, EMPTYSTRVALUE);
		this.refreshLocStrValidator();
	}
	
}