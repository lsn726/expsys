package com.logsys.prodplan;

import java.util.List;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/**
 * �����ƻ�������
 * @author lx8sn6
 */
public class ProdplanUtils {

	private static final Logger logger=Logger.getLogger(ProdplanUtils.class);
	
	/**
	 * ��pplistȷ�����������ƻ�������
	 * @param pplist
	 * @return ʱ������
	 */
	public static DateInterval getDataInterval(List<ProdplanContent> pplist) {
		if(pplist==null) {
			logger.error("����ȷ�����䣬��Ϊ����Ϊ�ա�");
			return null;
		}
		if(pplist.size()==0) return null;
		DateInterval dinterval=new DateInterval();
		dinterval.begindate=pplist.get(0).getDate();		//��ʼʱ��ͽ���ʱ�䶼��ʼ��Ϊ0
		dinterval.enddate=pplist.get(0).getDate();
		for(ProdplanContent ppcont:pplist) {				//ѭ����Ѱ��ʼʱ��ͽ���ʱ��
			if(ppcont.getDate().before(dinterval.begindate)) dinterval.begindate=ppcont.getDate();
			if(ppcont.getDate().after(dinterval.enddate)) dinterval.enddate=ppcont.getDate();
		}
		return dinterval;
	}
	
}
