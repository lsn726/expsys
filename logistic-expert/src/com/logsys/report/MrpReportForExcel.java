package com.logsys.report;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.logsys.bom.BOMUtil;
import com.logsys.material.MaterialContent;
import com.logsys.material.MaterialDataReaderDB;
import com.logsys.material.MaterialUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.prodplan.ProdplanContent;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.util.DateInterval;
import com.logsys.util.Matrixable;

/**
 * 创建Mrp报告
 * @author lx8sn6
 */
public class MrpReportForExcel {

	private static final Logger logger=Logger.getLogger(MrpReportForExcel.class);
	
	/**原材料顺序*/
	Map<String,Integer> matorder_raw;
	
	/**原材料列表*/
	List<MaterialContent> matlist_raw;
	
	/**原材料pn集*/
	Set<String> matset_raw;
	
	/**产成品顺序*/
	Map<String,Integer> matorder_fin;
	
	/**产成品列表*/
	List<ModelContent> matlist_fin;
	
	/**产成品集*/
	Set<String> matset_fin;
	
	/**BOM图*/
	Map<String,Map<String,Double>> bommap;
	
	/**生产计划列表*/
	List<ProdplanContent> pplist;
	
	/**
	 * 生成报告函数，并存储于filepath中
	 * @param filepath 需要存储的文件路径
	 */
	public void generate(String filepath) {
		logger.fatal(init());
	}
	
	/**初始化所有需要的功能呢*/
	private boolean init() {
		matlist_raw=MaterialDataReaderDB.getDataFromDB(null, "buy", true, true);	//初始化原材料列表
		if(matlist_raw==null) return false;
		matset_raw=MaterialUtil.getPnSet(matlist_raw);				//初始化原材料集
		if(matset_raw==null) return false;
		matorder_raw=MaterialUtil.getOrderedMatMap(matset_raw);		//获取原材料顺序图
		if(matorder_raw==null) return false;
		matlist_fin=ModelDataReaderDB.getDataFromDB(null);			//初始化成品列表，获取所有Model
		if(matlist_fin==null) return false;
		matset_fin=ModelUtil.getModelSet(matlist_fin);				//初始化成品集
		if(matset_fin==null) return false;
		matorder_fin=ModelDataReaderDB.sortModels(matset_fin);		//初始化成品顺序图
		if(matorder_fin==null) return false;
		bommap=BOMUtil.getRowBomMatrix(matset_fin);					//获取所有BOM集
		if(bommap==null) return false;
		pplist=ProdplanDataReaderDB.getProdplan(new DateInterval(null,null));//获取计划列表
		if(pplist==null) return false;
		return true;
	}
	
	
	//public Matrixable genMatrix() {
		
	//}
	
}
