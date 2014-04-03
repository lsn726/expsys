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
	 * @return д�������ƻ�����Ŀ����
	 */
	public static int importProdplanFromExcel(String filepath, Date startdate, Date enddate) {
		//���ȴ�Excel�ļ��ж�ȡ�ƻ�
		List<ProdplanContent> pplist=ProdplanDataReaderExcel.getFAPlanFromFileBWI(filepath, startdate, enddate);
		if(pplist==null) return -1;
		//ȷ�϶�ȡ�ļƻ�����
		DateInterval dinterval=ProdplanUtils.getDataInterval(pplist);
		if(dinterval==null) return -1;
		//TODO: ɾ��ԭ����ƻ�
		//д���¼ƻ�
		int writecounter=ProdplanDataWriterDB.writeToDB(pplist);
		if(writecounter<0) return -1;
		//�������䣬�汾Ϊ��ǰʱ��
		int bkupcounter=ProdplanDataWriterDB.backupProdPlan(dinterval, null);
		if(bkupcounter<0) logger.error("���棡��Ȼ�¼ƻ�д��ɹ�������û�гɹ��������ݣ�");
		return writecounter;
	}


}