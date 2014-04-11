package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI��������Ϣ������
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {

	/**
	 * ��ȡ��װ1������
	 * @return ��װ1������
	 */
	public abstract String getFA1Name();
	
	/**
	 * ��ȡ��װ2������
	 * @return ��װ2������
	 */
	public abstract String getFA2Name();
	
	/**
	 * ͨ��������ȡ��׼����������
	 * @param alias ����
	 * @return ����������/ʧ��null
	 */
	public abstract String getStdProdlineNameByAlias(String alias);
	
	/**
	 * ������Ϣ��汾
	 * @return �汾
	 */
	public abstract Date getVersion();
	
}
