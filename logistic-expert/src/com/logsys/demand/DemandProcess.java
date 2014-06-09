package com.logsys.demand;

import org.apache.log4j.Logger;

/**
 * �������̣�����������ع��ܵ������ڴ����м��С�
 * @author lx8sn6
 */
public class DemandProcess {

	private static Logger logger=Logger.getLogger(DemandProcess.class);
	
	/**
	 * ��Excel�ļ��е�����������ȡ�����ݣ�ɾ��ԭ���ݣ���д�����ݿ⡣
	 * @param filepath Excel�ļ�·��
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean importDemandFromExcel(String filepath) {
		DemandExcelService demExcelService=DemandExcelService.getDemandExcelService(filepath);
		if(demExcelService==null) {
			logger.error("���ܴ�Excel�е����������ݣ�����Excel���񴴽�ʧ�ܡ�");
			return false;
		}
		return demExcelService.syncDatabase();
	}

}
