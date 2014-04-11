package com.logsys.prodplan;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/**
 * �����ƻ�����
 * @author lx8sn6
 */
public class ProdplanProcess {

	private static Logger logger=Logger.getLogger(ProdplanProcess.class);
	
	/**
	 * ��Excel���ж�ȡ�����ƻ����ݲ�д�����ݿ�
	 * @param filepath ��ϸ�ļ�·��
	 * @param startdate ��ʼ����,null��Ĭ��Ϊ��һ�ܵ���һ
	 * @param enddate ��������,null����������
	 * @param resetPlanBeyondScope �Ƿ�������������δ���ļƻ������Ƿ�ɾ��ָ������δ���ڼ��ļƻ�
	 * @return д�������ƻ�����Ŀ����
	 */
	public static int importProdplanFromExcel(String filepath, Date startdate, Date enddate, boolean resetPlanBeyondScope) {
		//���ȴ�Excel�ļ��ж�ȡ�ƻ�
		List<ProdplanContent> pplist=ProdplanDataReaderExcel.getFAPlanFromFileBWI(filepath, startdate, enddate);
		if(pplist==null) return -1;
		//ȷ�϶�ȡ�ļƻ�����
		DateInterval dinterval=ProdplanUtils.getDataInterval(pplist);
		if(dinterval==null) return -1;
		//ɾ��ԭ����ƻ�
		int deletecounter=ProdplanDataWriterDB.deleteProdplan(dinterval,resetPlanBeyondScope);
		if(deletecounter<0) return -1;
		//д���¼ƻ�
		int writecounter=ProdplanDataWriterDB.writeToDB(pplist);
		if(writecounter<0) return -1;
		//�������䣬�汾Ϊ��ǰʱ��
		int bkupcounter=ProdplanDataWriterDB.backupProdPlan(dinterval, null);
		if(bkupcounter<0) logger.error("���棡��Ȼ�¼ƻ�д��ɹ�������û�гɹ��������ݣ�");
		return writecounter;
	}


}