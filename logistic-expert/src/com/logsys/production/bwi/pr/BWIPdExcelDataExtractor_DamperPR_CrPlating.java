package com.logsys.production.bwi.pr;

/**
 * BWI����Excel��Ϣ��ȡ��--������PR��ȡ��--�����
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_CrPlating extends BWIPdExcelDataExtractor_DamperPR {
	
	public BWIPdExcelDataExtractor_DamperPR_CrPlating() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0��00-01��15");
		initLocValidatorStrMap();
	}

}
