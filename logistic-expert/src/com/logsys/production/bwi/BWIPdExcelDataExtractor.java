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
	
	public static final String EMPTYSTRVALUE="EMPTYSTRVALUE";
	
	/*��֤�ַ���ö�ٱ�*/
	public static enum ValidatorStr {
		/**�������1*/
		Interval_Early1,
		/**�������2*/
		Interval_Early2,
		/**�������3*/
		Interval_Early3,
		/**�������4*/
		Interval_Early4,
		/**�������5*/
		Interval_Early5,
		/**�������6*/
		Interval_Early6,
		/**�������7*/
		Interval_Early7,
		/**�������8*/
		Interval_Early8,
		/**�������9*/
		Interval_Early9,
		/**�а�����1*/
		Interval_Middle1,
		/**�а�����2*/
		Interval_Middle2,
		/**�а�����3*/
		Interval_Middle3,
		/**�а�����4*/
		Interval_Middle4,
		/**�а�����5*/
		Interval_Middle5,
		/**�а�����6*/
		Interval_Middle6,
		/**�а�����7*/
		Interval_Middle7,
		/**�а�����8*/
		Interval_Middle8,
		/**�а�����9*/
		Interval_Middle9,
		/**�������1*/
		Interval_Night1,
		/**�������2*/
		Interval_Night2,
		/**�������3*/
		Interval_Night3,
		/**�������4*/
		Interval_Night4,
		/**�������5*/
		Interval_Night5,
		/**�������6*/
		Interval_Night6,
		/**�������7*/
		Interval_Night7,
		/**�������8*/
		Interval_Night8,
		/**�������9*/
		Interval_Night9
	}
	
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
