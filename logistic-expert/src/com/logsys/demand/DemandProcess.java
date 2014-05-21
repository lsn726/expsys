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
	 * ��pnset�е��ͺţ���begin��end֮������󣬵������ļ�exportfilepath,���begin��/endΪnull����Ĭ��Ϊ��Ӧ���ڲ�����
	 * @param exportfilepath �ļ�·��
	 * @param pnset �ͺż�,���Ϊnull,�������ͺ�
	 * @param begin ��ʼʱ��,�����null����Ĭ��Ϊ��������ʼʱ��
	 * @param end ����ʱ�䣬�����null,��Ĭ��Ϊ�����ƽ���ʱ��
	 * @return �ɹ�/true,ʧ��/false
	 */
	@Deprecated
	public static boolean exportDemandToExcel(String exportfilepath,Set<String> pnset, Date begin, Date end) {
		List<DemandContent> demlist=DemandDataReaderDB.getDemandDataFromDB_OnDay(pnset, begin, end);	//���ȴ����ݿ����ȡ����
		if(demlist==null) return false;
		Map<String,Map<Date,DemandContent>> demmap=DemandUtil.demListToMapByPn(demlist);	//�Ӷ�ȡ��Listת���ɰ����ͺź�ʱ�����ĸ�ʽ
		if(demmap==null) return false;
		return DemandDataWriterExcel.writeToExcel(exportfilepath, demmap);		//д���ļ�·��
	}
	
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
	
	
}
