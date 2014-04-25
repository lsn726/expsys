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
 * ����Mrp����
 * @author lx8sn6
 */
public class MrpReportForExcel {

	private static final Logger logger=Logger.getLogger(MrpReportForExcel.class);
	
	/**ԭ����˳��*/
	Map<String,Integer> matorder_raw;
	
	/**ԭ�����б�*/
	List<MaterialContent> matlist_raw;
	
	/**ԭ����pn��*/
	Set<String> matset_raw;
	
	/**����Ʒ˳��*/
	Map<String,Integer> matorder_fin;
	
	/**����Ʒ�б�*/
	List<ModelContent> matlist_fin;
	
	/**����Ʒ��*/
	Set<String> matset_fin;
	
	/**BOMͼ*/
	Map<String,Map<String,Double>> bommap;
	
	/**�����ƻ��б�*/
	List<ProdplanContent> pplist;
	
	/**
	 * ���ɱ��溯�������洢��filepath��
	 * @param filepath ��Ҫ�洢���ļ�·��
	 */
	public void generate(String filepath) {
		logger.fatal(init());
	}
	
	/**��ʼ��������Ҫ�Ĺ�����*/
	private boolean init() {
		matlist_raw=MaterialDataReaderDB.getDataFromDB(null, "buy", true, true);	//��ʼ��ԭ�����б�
		if(matlist_raw==null) return false;
		matset_raw=MaterialUtil.getPnSet(matlist_raw);				//��ʼ��ԭ���ϼ�
		if(matset_raw==null) return false;
		matorder_raw=MaterialUtil.getOrderedMatMap(matset_raw);		//��ȡԭ����˳��ͼ
		if(matorder_raw==null) return false;
		matlist_fin=ModelDataReaderDB.getDataFromDB(null);			//��ʼ����Ʒ�б���ȡ����Model
		if(matlist_fin==null) return false;
		matset_fin=ModelUtil.getModelSet(matlist_fin);				//��ʼ����Ʒ��
		if(matset_fin==null) return false;
		matorder_fin=ModelDataReaderDB.sortModels(matset_fin);		//��ʼ����Ʒ˳��ͼ
		if(matorder_fin==null) return false;
		bommap=BOMUtil.getRowBomMatrix(matset_fin);					//��ȡ����BOM��
		if(bommap==null) return false;
		pplist=ProdplanDataReaderDB.getProdplan(new DateInterval(null,null));//��ȡ�ƻ��б�
		if(pplist==null) return false;
		return true;
	}
	
	
	//public Matrixable genMatrix() {
		
	//}
	
}
