package com.logsys.model;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 模型数据读取器
 * @author lx8sn6
 */
public class ModelDataReader {

	private static final Logger logger=Logger.getLogger(ModelDataReader.class);
	
	/**
	 * 根据modelset所提供的型号查询排序，返回Map<型号,顺序>。排序依据为：client,prjcode,info,client
	 * @param modelset 需要排序的型号集
	 * @return Map<型号,排序>对象
	 */
	private static Map<ModelContent,Integer> sortModels(Set<String> modelset) {
		return null;
	}
	
}
