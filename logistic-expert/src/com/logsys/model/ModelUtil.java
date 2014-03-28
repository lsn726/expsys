package com.logsys.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 成品工具类
 * @author lx8sn6
 */
public class ModelUtil {

	private static Logger logger=Logger.getLogger(ModelUtil.class);
	
	/**
	 * 从模型列表中提取模型集
	 * @param modellist 模型列表
	 * @return 模型集
	 */
	public static Set<String> getModelSet(List<ModelContent> modellist) {
		if(modellist==null) {
			logger.error("不能提取模型集，参数为空");
			return null;
		}
		Set<String> modelset=new HashSet<String>();
		for(ModelContent mcont:modellist)
			modelset.add(mcont.getModel());
		return modelset;
	}
	
}
