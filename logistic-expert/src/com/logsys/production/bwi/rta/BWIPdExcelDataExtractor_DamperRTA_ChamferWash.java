package com.logsys.production.bwi.rta;



/**
 * Damper Chamfering and Washing������ϴ������ȡ��
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperRTA_ChamferWash extends BWIPdExcelDataExtractor_DamperRTA {

	public BWIPdExcelDataExtractor_DamperRTA_ChamferWash() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle1, "17��00-18��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle2, "18��00-19��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle3, "19��00-20��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "20��00-21:00");
		validatorStrMap.put(ValidatorStr.Interval_Middle5, "21��00-22��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle6, "22��00-23��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle7, "23��00-00��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "00��00-01��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle9, "01��00-01��30");
		validatorStrMap.put(ValidatorStr.Interval_Night1, "02��30-03��30");
		validatorStrMap.put(ValidatorStr.Interval_Night2, "03��30-04��30");
		validatorStrMap.put(ValidatorStr.Interval_Night3, "04��30-05��30");
		validatorStrMap.put(ValidatorStr.Interval_Night4, "05��30-06��30");
		validatorStrMap.put(ValidatorStr.Interval_Night5, "06��30-07��30");
		validatorStrMap.put(ValidatorStr.Interval_Night6, "07��30-08��00");
		validatorStrMap.put(ValidatorStr.Interval_Night7, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night8, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night9, EMPTYSTRVALUE);
		initLocValidatorStrMap();
	}
	
}