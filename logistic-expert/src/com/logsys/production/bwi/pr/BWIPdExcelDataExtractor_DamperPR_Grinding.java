package com.logsys.production.bwi.pr;

/**
 * BWI生产Excel信息提取器--减震器PR提取器--镀前研磨
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_Grinding extends BWIPdExcelDataExtractor_DamperPR {
	
	public BWIPdExcelDataExtractor_DamperPR_Grinding() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0：00-1：15");
		initLocValidatorStrMap();
	}

}
