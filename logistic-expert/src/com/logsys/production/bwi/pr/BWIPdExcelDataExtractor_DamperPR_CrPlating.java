package com.logsys.production.bwi.pr;

/**
 * BWI生产Excel信息提取器--减震器PR提取器--电镀线
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_CrPlating extends BWIPdExcelDataExtractor_DamperPR {
	
	public BWIPdExcelDataExtractor_DamperPR_CrPlating() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0：00-01：15");
		initLocValidatorStrMap();
	}

}
