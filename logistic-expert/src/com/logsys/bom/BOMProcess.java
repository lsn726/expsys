package com.logsys.bom;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM������
 * @author lx8sn6
 */
public class BOMProcess {

	private static final Logger logger=Logger.getLogger(BOMProcess.class);
	
	/**
	 * ��SAP��CS12�������е�BOM���뵽���ݿ���
	 * @param filepath CS12��TCode����������ļ�
	 * @return д���¼������/-1ʧ��
	 */
	public static int uploadBOMFromExcel_SAPCS12(String filepath) {
		List<BOMContent> bomlist=BOMDataReaderExcel.getDataFromExcel_CS12(filepath);	//���ȴ�Excel���ж�ȡ�б�
		if(bomlist==null) return -1;
		int bkupcounter=BOMDataWriterDB.backupComplBOMFromTopNode(bomlist.get(0).getPn(), new Date());	//���ݶ�ȡ���б�����ԭBOM�еĶ����ڵ��¾�BOM
		if(bkupcounter==-1)
			logger.warn("�޷�����ԭ�б�,�������µ�BOM.");
		int delcounter=BOMDataWriterDB.delComplBOMFromTopNode(bomlist.get(0).getPn());	//ɾ��ԭBOM
		if(delcounter==-1)
			if(bkupcounter==-1)
				logger.warn("�޷�ɾ��ԭ�б�,��Ϊ���µ�BOM.");
			else {
				logger.error("�����Ѿ�������ԭ�б�����δ�ܳɹ�ɾ�����BOM,���ֶ�ɾ������BOM��");
				return -1;
			}
		int writecounter=BOMDataWriterDB.writeDataToDB(bomlist, new Date());			//д����BOM
		return writecounter;
	}
	
}
