package com.logsys.bom;

import java.util.List;

/**
 * BOM������
 * @author lx8sn6
 */
public class BOMProcess {

	/**
	 * ��SAP��CS12�������е�BOM���뵽���ݿ���
	 * @param filepath CS12��TCode����������ļ�
	 * @return д���¼������/-1ʧ��
	 */
	public static int uploadBOMFromExcel_SAPCS12(String filepath) {
		List<BOMContent> bomlist=BOMDataReaderExcel.getDataFromExcel_CS12(filepath);
		if(bomlist==null) return -1;
		
		return -1;
	}
	
}
