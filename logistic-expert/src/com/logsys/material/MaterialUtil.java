package com.logsys.material;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 物料工具类
 * @author lx8sn6
 *
 */
public class MaterialUtil {

	private static Logger logger=Logger.getLogger(MaterialUtil.class);
	
	/**
	 * 将matlist物料列表转换为Map<pn,MaterialContent>物料图
	 * @param matlist 物料列表
	 * @return 物料图
	 */
	public static Map<String,MaterialContent> matListToMap(List<MaterialContent> matlist) {
		if(matlist==null) {
			logger.error("不能转换为物料图，物料列表为空.");
			return null;
		}
		Map<String,MaterialContent> matmap=new HashMap<String,MaterialContent>();
		if(matlist.size()==0) return matmap;
		for(MaterialContent matcont:matlist)
			matmap.put(matcont.getPn(), matcont);
		return matmap;
	}
	
	/**
	 * 获取经过排序的物料图
	 * @param pnset 物料集
	 * @return Map<pn,顺序>对象
	 */
	public static Map<String,Integer> getOrderedMatMap(Set<String> pnset) {
		List<MaterialContent> orderedlist=MaterialDataReaderDB.getDataFromDB(pnset, "buy", true, true);
		if(orderedlist==null) return null;
		Map<String,Integer> orderedmap=new HashMap<String,Integer>();
		int counter=1;
		for(MaterialContent mcont:orderedlist)		//将orderedslist转化为orderedmap
			orderedmap.put(mcont.getPn(), counter++);
		return orderedmap;
	}
	
	/**
	 * 从物料列表中提取物料pn集
	 * @param matlist 物料列表
	 * @return 物料pn集
	 */
	public static Set<String> getPnSet(List<MaterialContent> matlist) {
		if(matlist==null) {
			logger.error("无法提取物料号列表.");
			return null;
		}
		Set<String> pnset=new HashSet<String>();
		for(MaterialContent mcont:matlist)
			pnset.add(mcont.getPn());
		return pnset;
	}
	
	
	
}