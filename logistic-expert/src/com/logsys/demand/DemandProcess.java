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
	 * @param pnset �ͺż�
	 * @param begin ��ʼʱ��,�����null����Ĭ��Ϊ��������ʼʱ��
	 * @param end ����ʱ�䣬�����null,��Ĭ��Ϊ�����ƽ���ʱ��
	 * @return �ɹ�/true,ʧ��/false
	 */
	public static boolean exportDemandToExcel(String exportfilepath,Set<String> pnset, Date begin, Date end) {
		if(exportfilepath==null) {
			logger.error("�ļ�·������Ϊ��.");
			return false;
		}
		List<DemandContent> demlist=DemandDataReaderDB.getDataFromDB(pnset, begin, end);	//���ȴ����ݿ����ȡ����
		if(demlist==null) return false;
		Map<String,Map<Date,DemandContent>> demmap=DemandUtil.demListToMapByPn(demlist);	//�Ӷ�ȡ��Listת���ɰ����ͺź�ʱ�����ĸ�ʽ
		if(demmap==null) return false;
		return DemandDataWriterExcel.writeToExcel(exportfilepath, demmap);		//д���ļ�·��
	}
	
	
}
