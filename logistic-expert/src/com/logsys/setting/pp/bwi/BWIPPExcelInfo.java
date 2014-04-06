package com.logsys.setting.pp.bwi;

import java.util.Date;

/**
 * BWI�����ƻ�Excel�����Ϣ
 * @author ShaonanLi
 */
public interface BWIPPExcelInfo {

	/**
	 * ��ȡpn��������
	 * @return pn��������
	 */
	public int getPnCol();
	
	/**
	 * ��ȡ��װ1�ߵ���������
	 * @return ��װ1����������
	 */
	public int getFA1DateRow();
	
	/**
	 * ��ȡ��װ1�߼ƻ���ʼ����
	 * @return ��װ1�߼ƻ���ʼ����
	 */
	public int getFA1PPBeginRow();
	
	/**
	 * ��ȡ��װ1�߼ƻ���������
	 * @return ��װ1�߼ƻ���������
	 */
	public int getFA1PPEndRow();
	
	/**
	 * ��ȡ��װ2����������
	 * @return ��װ2����������
	 */
	public int getFA2DateRow();
	
	/**
	 * ��ȡ��װ2�߼ƻ���ʼ����
	 * @return ��װ2�߼ƻ���ʼ����
	 */
	public int getFA2PPBeginRow();
	
	
	/**
	 * ��ȡ��װ2�߼ƻ���������
	 * @return ��װ2�߼ƻ���������
	 */
	public int getFA2PPEndRow();
	
	/**
	 * ��ȡ�汾 
	 * @return �汾
	 */
	public Date getVersion();
	
}
