package com.logsys.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel工具类
 * @author lx8sn6
 */
public class ExcelUtils {

	private static final Logger logger=Logger.getLogger(ExcelUtils.class);
	
	/**
	 * Excel行是否为空，从参照行的起始单元开始，只要参照行单元格不为空，目标行单元格出现一个不为空，既不是空行。
	 * @param refrow 参照行
	 * @param refstart 参照起始位置
	 * @param targetrow 目标行
	 * @param targetstart 目标行起始位置
	 * @return true是空行/false不是空行
	 */
	public static boolean isExcelRowEmpty(Row refrow, int refstart, Row targetrow, int targetstart) {
		if(refrow==null||targetrow==null) {
			logger.error("不能判断Excel行是否为空，参照行或者目标行对象为空。");
			return false;
		}
		if(refstart<0||targetstart<0) {
			logger.error("不能判断Excel行是否为空，起始单元格列数小于0。");
			return false;
		}
		for(;refrow.getCell(refstart)!=null;refstart++,targetstart++)	//从参照行开始遍历
			if(targetrow.getCell(targetstart)!=null) return false;		//只要存在单元格，即不为空
		return true;
	}

}
