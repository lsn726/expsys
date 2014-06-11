package com.logsys.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel������
 * @author lx8sn6
 */
public class ExcelUtils {

	private static final Logger logger=Logger.getLogger(ExcelUtils.class);
	
	/**
	 * Excel���Ƿ�Ϊ�գ��Ӳ����е���ʼ��Ԫ��ʼ��ֻҪ�����е�Ԫ��Ϊ�գ�Ŀ���е�Ԫ�����һ����Ϊ�գ��Ȳ��ǿ��С�
	 * @param refrow ������
	 * @param refstart ������ʼλ��
	 * @param targetrow Ŀ����
	 * @param targetstart Ŀ������ʼλ��
	 * @return true�ǿ���/false���ǿ���
	 */
	public static boolean isExcelRowEmpty(Row refrow, int refstart, Row targetrow, int targetstart) {
		if(refrow==null||targetrow==null) {
			logger.error("�����ж�Excel���Ƿ�Ϊ�գ������л���Ŀ���ж���Ϊ�ա�");
			return false;
		}
		if(refstart<0||targetstart<0) {
			logger.error("�����ж�Excel���Ƿ�Ϊ�գ���ʼ��Ԫ������С��0��");
			return false;
		}
		for(;refrow.getCell(refstart)!=null;refstart++,targetstart++)	//�Ӳ����п�ʼ����
			if(targetrow.getCell(targetstart)!=null) return false;		//ֻҪ���ڵ�Ԫ�񣬼���Ϊ��
		return true;
	}

}
