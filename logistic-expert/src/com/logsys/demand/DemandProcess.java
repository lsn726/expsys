package com.logsys.demand;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * �������̣�����������ع��ܵ������ڴ����м��С�
 * @author lx8sn6
 */
public class DemandProcess {

	private static Logger logger=Logger.getLogger(DemandProcess.class);
	
	/**
	 * ��importfilepath�е�Excel�����ݵ��뵽���ݿ��Demand���У���������ǰ����
	 * @param importfilepath �����������ݵ��ļ�����ʽΪ����һ�����ڣ��ڶ����ͺţ�����������
	 * @param version ����汾��nullΪ��ǰʱ��
	 * @return �ɹ�д�����Ŀ����,-1Ϊʧ��
	 */
	public static int importDemandFromExcel(String importfilepath, Date version) {
		List<DemandContent> importdemlist=DemandDataReaderExcel.getDataFromFile(importfilepath);	//���ļ��л�ȡ��������
		if(importdemlist==null) return -1;
		Map<String,Map<Date,DemandContent>> importdemmap=DemandUtil.demListToMapByPn(importdemlist);//�����list->map
		if(importdemmap==null) return -1;
		int insertqty=DemandDataWriterDB.updateOrInsert(importdemmap);				//������mapд�����ݿ�
		if(insertqty<0) return -1;
		int backupqty=DemandDataWriterDB.backupDemandData(importdemmap,version);	//��ԭ�°汾���ݽ��б���
		if(backupqty<0) logger.error("�������ݳ��ִ���.");
		logger.info("���ݴ�Excel����ɹ�,��������:"+backupqty+",��������:"+insertqty+".");
		return insertqty;
	}
	
	/**
	 * ��Excel�ļ��е�����������ȡ�����ݣ�ɾ��ԭ���ݣ���д�����ݿ⡣
	 * @param filepath Excel�ļ�·��
	 * @param version ����汾,nullΪ��ǰʱ��
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean importDemandFromExcel_v2(String filepath, Date version) {
		DemandExcelService demExcelService=DemandExcelService.getDemandExcelService(filepath);
		if(demExcelService==null) {
			logger.error("���ܴ�Excel�е����������ݣ�����Excel���񴴽�ʧ�ܡ�");
			return false;
		}
		return true;
	}

}
