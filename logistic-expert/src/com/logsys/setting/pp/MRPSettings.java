package com.logsys.setting.pp;


/**
 * MRP���ýӿ�
 * @author ShaonanLi
 */
public interface MRPSettings {

	/**
	 * �������߻�ȡ������
	 * @param fertpn ��Ʒ��
	 * @return ������
	 */
	public Float getScarpRateByPN(String fertpn);
	
	/**
	 * ��ȡ����ȡ����ֵ
	 * @param fertpn ��Ʒ��
	 * @return ����ȡ��ֵ
	 */
	public int getCeilingValueByPN(String fertpn);
	
}
