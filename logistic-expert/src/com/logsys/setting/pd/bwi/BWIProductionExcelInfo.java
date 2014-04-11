package com.logsys.setting.pd.bwi;

import java.util.Date;

import com.logsys.util.Location;

/**
 * BWI������Excel��Ϣ�ӿ�
 * @author ShaonanLi
 */
public interface BWIProductionExcelInfo {
	
	/**
	 * ��ȡ����Sheet����
	 * @return ����Sheet����
	 */
	public String getConfigSheetName();
	
	/**
	 * ��ȡ����Sheet�����������Ƶ�Ԫ���λ��
	 * @return ����Sheet�����������Ƶ�Ԫ��λ��
	 */
	public Location getPrdLineNameLocInConfigSheet();
	
	/**
	 * ��ȡ����Sheet���·���Ϣ��Ԫ��λ��
	 * @return �·���Ϣ��Ԫ��λ��
	 */
	public Location getDateInfoLocInConfigSheet();

	/**
	 * ��ȡ����sheet�в���������������
	 * @return ����������������
	 */
	public int getOperatorQtyColInProdSheet();

	/**
	 * ��ȡ����sheet��Part Number������
	 * @return part number������
	 */
	public int getPnColInProdSheet();
	
	/**
	 * ��ȡ����Sheet�в�������������
	 * @return ��������������
	 */
	public int getOutputQtyColInProdSheet();
	
	/**
	 * ��ȡ����Sheet��ʱ�����ʼ������
	 * @return ʱ�����ʼ������
	 */
	public int getTimeFrameBeginColInProdSheet();
	
	/**
	 * ��ȡ����Sheet��ʱ��ν���������
	 * @return ʱ��ν���������
	 */
	public int getTimeFrameEndColInProdSheet();
	
	/**
	 * ��ȡ�����ʼ��
	 * @return �����ʼ��
	 */
	public int getEarlyShiftStartColInProdSheet();
	
	/**
	 * ��ȡ�а���ʼ�� 
	 * @return �а���ʼ��
	 */
	public int getMiddleShiftStartColInProdSheet();
	
	/**
	 * ��ȡ�����ʼ��
	 * @return �����ʼ��
	 */
	public int getNightShiftStartColInProdSheet();
	
	/**
	 * ��ȡ������Ϣ��ʵ����
	 * @return
	 */
	public int getProductionBeginRowInProdSheet();
	
	/**
	 * ��ȡ���ð汾
	 * @return ���ð汾
	 */
	public Date getVersion();
	
}
