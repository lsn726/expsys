package com.logsys.setting.pd.bwi;

import com.logsys.util.Location;

/**
 * BWI������Excel��Ϣ������
 * @author ShaonanLi
 */
public abstract class BWIProductionExcelInfo {

	/**
	 * ��ȡ����Sheet����
	 * @return ����Sheet����
	 */
	public String getConfigSheetName() {
		return "Configuration";
	}
	
	/**
	 * ��ȡ����Sheet�����������Ƶ�Ԫ���λ��
	 * @return ����Sheet�����������Ƶ�Ԫ��λ��
	 */
	public Location getPrdLineNameLocInConfigSheet() {
		Location loc=new Location();
		loc.column=0;
		loc.row=1;
		return loc;
	}
	
	/**
	 * ��ȡ����Sheet���·���Ϣ��Ԫ��λ��
	 * @return �·���Ϣ��Ԫ��λ��
	 */
	public Location getDateInfoLocInConfigSheet() {
		Location loc=new Location();
		loc.column=1;
		loc.row=1;
		return loc;
	}

}
