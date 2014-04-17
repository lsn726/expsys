package com.logsys.production.bwi.pr;

import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA;
import com.logsys.setting.pd.bwi.BWIPLInfo;

/**
 * BWI����Excel��Ϣ��ȡ��--������RTA��ȡ��
 * @author lx8sn6
 */
public abstract class BWIPdExcelDataExtractor_DamperPR extends BWIPdExcelDataExtractor_DamperRTA {
	
	public BWIPdExcelDataExtractor_DamperPR() {
		super();
		PRDZONE=BWIPLInfo.STDNAME_DAMPER_PR;
	}
	
	protected void initValidatorStr() {
		super.initValidatorStr();	//���е���RTA�ĳ�ʼ���ַ���
		validatorStrMap.put(ValidatorStr.Interval_Middle1, "16��45-18��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle2, "18��00-19��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle3, "19��00-20��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle4, "20��00-21��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle5, "21��00-22��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle6, "22��00-23��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle7, "23��00-0��00");
		validatorStrMap.put(ValidatorStr.Interval_Middle8, "00��00-01��15");
		validatorStrMap.put(ValidatorStr.Interval_Middle9, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night1, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night2, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night3, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night4, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night5, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night6, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night7, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night8, EMPTYSTRVALUE);
		validatorStrMap.put(ValidatorStr.Interval_Night9, EMPTYSTRVALUE);
	}
	
	protected void initPrdAliasMap() {
		prdaliasmap.clear();
		prdaliasmap.put("22271386 C7 ǰ��", "22271386");
		prdaliasmap.put("22261386 C7ǰ���µ�", "22271386");
		prdaliasmap.put("22271387  C7 ǰ��", "22271387");
		prdaliasmap.put("22271402 C7 ��", "22271402");
		prdaliasmap.put("22271403 C7 ���", "22271403");
		prdaliasmap.put("22261415  L7 ��", "22261415");
		prdaliasmap.put("22261435  L7 ǰ", "22261435");
		prdaliasmap.put("22271172 CQAC ǰ", "22271172");
		prdaliasmap.put("22271119 CQAC��", "22271119");
		prdaliasmap.put("22262054 VOLVO ǰ", "22262054");
		prdaliasmap.put("222261455 VOLVO��", "22261455");
		prdaliasmap.put("22261455 VOLVO��", "22261455");
	}

}
