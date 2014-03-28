package com.logsys.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * ��Ʒ������
 * @author lx8sn6
 */
public class ModelUtil {

	private static Logger logger=Logger.getLogger(ModelUtil.class);
	
	/**
	 * ��ģ���б�����ȡģ�ͼ�
	 * @param modellist ģ���б�
	 * @return ģ�ͼ�
	 */
	public static Set<String> getModelSet(List<ModelContent> modellist) {
		if(modellist==null) {
			logger.error("������ȡģ�ͼ�������Ϊ��");
			return null;
		}
		Set<String> modelset=new HashSet<String>();
		for(ModelContent mcont:modellist)
			modelset.add(mcont.getModel());
		return modelset;
	}
	
}
