package com.logsys.production.bwi.pr;

/**
 * BWI����Excel��Ϣ��ȡ��--������PR��ȡ��--��ĥ��
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperPR_CoarseGrinding extends BWIPdExcelDataExtractor_DamperPR {

	public BWIPdExcelDataExtractor_DamperPR_CoarseGrinding() {
		super();
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "0:00-1:15");
		initLocValidatorStrMap();
	}
	
	
}