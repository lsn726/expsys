package com.logsys.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	/**
	 * 将List<ModelContent>转化为Map<pn,ModelContent>
	 * @param modellist List<ModelContent>对象
	 * @return Map<pn,ModelContent>对象/null
	 */
	public static Map<String,ModelContent> convModelListToModelMap(List<ModelContent> modellist) {
		if(modellist==null) {
			logger.error("不能模型列表转换为模型图。参数为空");
			return null;
		}
		HashMap<String,ModelContent> modelmap=new HashMap<String,ModelContent>();
		for(ModelContent modelcont:modellist)
			modelmap.put(modelcont.getModel(), modelcont);
		return modelmap;
	}
	
}
