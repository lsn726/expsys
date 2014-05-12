package com.logsys.matoperdoc;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * ���ϲ�����¼������
 * @author ShaonanLi
 */
public class MatOperDocContentProcess {

	private static final Logger logger=Logger.getLogger(MatOperDocContentProcess.class);
	
	/**
	 * ����SAP��MB51�е���������д�����ݿ�
	 * @param filepath �ļ�·��
	 * @return д������
	 */
	public static int importSAPMb51ExcelFileIntoDB(String filepath) {
		List<MatOperDocContent> doclist=MatOperDocContentSAPExcelReaderMB51.readDataFromSAPExcel(filepath);
		int qtywrited=MatOperDocContentDataWriterDB.writeToDB(doclist);
		logger.info("���ϲ������ݳɹ�д�����ݿ⡣���ϵ���["+doclist.size()+"]��,����д��["+qtywrited+"]����");
		return qtywrited;
	}
	
}
