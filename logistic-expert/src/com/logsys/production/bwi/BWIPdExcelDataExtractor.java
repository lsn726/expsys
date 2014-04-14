package com.logsys.production.bwi;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.logsys.production.LostTimeContent;
import com.logsys.production.ProductionContent;

/**
 * BWI�������ձ�Excel��Ϣ��ȡ��
 * @author ShaonanLi
 */
public interface BWIPdExcelDataExtractor {
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)��ȡdatasrc�еĲ�������
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ��ȡ�����������б�
	 */
	public abstract List<ProductionContent> extractOutputData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)��ȡdatasrc�е���ʧʱ������
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ��ȡ����ʧʱ�������б�
	 */
	public abstract List<LostTimeContent> extractLostTimeData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)���ж�datasrc�е����ݸ�ʽ�Ƿ���ȷ
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ͨ��������֤true/δͨ��false
	 */
	public abstract boolean validateDatasrc(Sheet datasrc,String stdprodline) throws Throwable;
	
}
