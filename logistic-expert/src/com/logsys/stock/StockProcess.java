package com.logsys.stock;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Stock������
 * @author lx8sn6
 */
public class StockProcess {

	private static final Logger logger=Logger.getLogger(StockProcess.class);
	
	/**
	 * ��SAP��MB52�������Excel�������д�����ݿ�
	 * @param filepath Excel�ļ�·��
	 * @param stockdate ����¼�����ڣ�null��Ĭ��Ϊ����
	 * @return д�����ݵ�����/-1ʧ��
	 */
	public static int importStockDataFromExcel_SAP_MB52(String filepath,Date stockdate) {
		ExcelStockService_SAP_MB52 service=ExcelStockService_SAP_MB52.getExcelReader("e:\\stock.xlsx");
		if(!service.extractStockList(new Date())) {
			logger.error("��ȡ�������ʱ���ִ���.����ʧ�ܡ�");
			return -1;
		}
		int writedqty=service.writeStockListToDB();
		if(writedqty<0) {
			logger.error("������б�д�����ݿ�ʱ���ִ��󡣲���ʧ��.");
			return -1;
		}
		logger.info("�������д��ɹ���д������["+writedqty+"]��.");
		return writedqty;
	}
	
}
