package com.logsys.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.logsys.bom.BOMUtil;
import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.material.MaterialContent;
import com.logsys.material.MaterialDataReaderDB;
import com.logsys.material.MaterialUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.prodplan.ProdplanContent_Week;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.production.ProductionContent_Week;
import com.logsys.production.ProductionDataReaderDB;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.Matrixable;

/**
 * ����Mrp����
 * @author lx8sn6
 */
public class MrpReportForExcel {

	private static final Logger logger=Logger.getLogger(MrpReportForExcel.class);
	
	/**ԭ����˳��*/
	Map<Integer,String> matorder_raw;
	
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
		return true;
	}
	
	/**
	 * �������������������ʽ���£�
	 * �б�ͷ��������ͺ�
	 * �б�ͷ�����������
	 * ����ֵ����������
	 * ԭ���мƻ����żƻ���û�ƻ����������в������üƻ�/�����ȥ����
	 * @param weeknum �����ھ�����������������������0
	 * @return �������
	 */
	public Matrixable getDemandMatrix(int weeknum) {
		init();
		if(weeknum<=0) {
			logger.error("���ܲ�������������������������0��");
			return null;
		}
		Set<ProdLine> plset=new HashSet<ProdLine>();
		plset.add(ProdLine.DAMPER_FA_FA1);
		plset.add(ProdLine.DAMPER_FA_FA2);
		List<ProductionContent_Week> pdwklist;					//���ܲ����б�
		List<DemandContent_Week> demwklist;						//���������б�
		List<ProdplanContent_Week> ppwklist;					//���������ƻ��б�
		Calendar begin=DateTimeUtils.getValidCalendar();
		Calendar end=DateTimeUtils.getValidCalendar();
		//���ܲ����б��ʼ��
		if(begin.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)	//����һ���ò�ѯ
			pdwklist=new ArrayList<ProductionContent_Week>();
		else {													//���������һ
			begin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	//��Ҫ�Ƚ���ʼʱ������Ϊ��һ
			Set<ProdLine> outputplset=new HashSet<ProdLine>();
			outputplset.add(ProdLine.DAMPER_FA_FINAL_CHECK);
			pdwklist=ProductionDataReaderDB.getOnWeekProductionDataFromDB(outputplset, new DateInterval(begin.getTime(),null));
			if(pdwklist==null) return null;
		}
		//���������б��ʼ��
		begin=DateTimeUtils.getValidCalendar();
		begin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		//��������ʼʱ��Ϊ������һ
		end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end.add(Calendar.WEEK_OF_YEAR, weeknum);				//ĩβ����Ϊweeknum�ܺ����ĩ
		demwklist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begin.getTime(), end.getTime());
		if(demwklist==null) return null;
		//���������ƻ��б��ʼ��
		ppwklist=ProdplanDataReaderDB.getOnWeekProdplanDataFromDB(plset, new DateInterval(begin.getTime(),null));
		if(ppwklist==null) return null;
		Matrixable demandMatrix=new Matrixable();//����������
		//��ʼ����ͷ
		for(String fertpn:matorder_fin.keySet())				//����Ʒ��˳��д���б�ͷ
			demandMatrix.putRowHeaderCell(matorder_fin.get(fertpn)+1, fertpn);
		begin=DateTimeUtils.getValidCalendar();
		begin.setFirstDayOfWeek(Calendar.MONDAY);				//��һΪÿ�ܵ�һ��
		for(int index=0;index<weeknum;index++)	{				//���������д���б�ͷ
			demandMatrix.putColHeaderCell(index+1, String.format("%dwk%02d", begin.get(Calendar.YEAR) ,begin.get(Calendar.WEEK_OF_YEAR)));
			begin.add(Calendar.WEEK_OF_YEAR, 1);
		}
		//д��������ݣ�����д�밴������
		Integer rowindex;			//������
		Integer colindex;			//������
		Float scarpRate;			//������
		Integer ceilValue;			//����ȡ����
		for(DemandContent_Week wkdem:demwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkdem.getPn());	//���ݳ�Ʒ�Ż�ȡ������
			if(rowindex==null) {
				logger.error("���ܲ��������������������еĳ�Ʒ����["+wkdem.getPn()+"]�������б�ͷ�ж�λ��������ϸ��"+wkdem.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkdem.getYear(),wkdem.getWeek()));	//�����������ȷ��������
			if(colindex==null) {
				logger.warn("����������е���/�������["+String.format("%dwk%02d",wkdem.getYear(),wkdem.getWeek())+"]�������б�ͷ�ж�λ��������ϸ��"+wkdem.toString()+".���ܳ�Խ�˹涨����������������������������");
				continue;
			}
			scarpRate=Settings.mrpSetting.getScarpRateByPN(wkdem.getPn());//��ȡ������
			if(scarpRate==null) {
				logger.error("���ܲ����������д��������ʱ����Ʒ����["+wkdem.getPn()+"]û�б����ʡ���ȷ�Ϻ����Ƿ���ȷ��������MRPSettings����ӱ����ʡ�");
				return null;
			}
			ceilValue=Settings.mrpSetting.getCeilingValueByPN(wkdem.getPn());//��ȡ����ȡ��ֵ
			if(ceilValue==null) {
				logger.error("���ܲ����������д��������ʱ����Ʒ����["+wkdem.getPn()+"]û������ȡ��������ȷ�Ϻ����Ƿ���ȷ��������MRPSettings���������ȡ������");
				return null;
			}
			//System.out.println(wkdem.getPn()+"["+rowindex+"]/["+colindex+"]/["+wkdem.getQty()+"]");
			demandMatrix.setData(rowindex, colindex, Math.ceil(wkdem.getQty()*(1+scarpRate)/ceilValue)*ceilValue);	//����λ��д������
		}
		//д��������ݣ�Prodplan����Demand
		for(ProdplanContent_Week wkpp:ppwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkpp.getPn());	//���ݳ�Ʒ�Ż�ȡ������
			if(rowindex==null) {
				logger.error("���ܲ�����������ܼƻ������еĳ�Ʒ����["+wkpp.getPn()+"]�����ڱ�ͷ�ж�λ��������ϸ��"+wkpp.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkpp.getYear(),wkpp.getWeek()));	//�����������ȷ��������
			if(colindex==null) {
				logger.warn("�ܼƻ������е���/�������["+String.format("%dwk%02d",wkpp.getYear(),wkpp.getWeek())+"]�������б�ͷ�ж�λ��������ϸ��"+wkpp.toString()+".���ܳ�Խ�˹涨����������������������������");
				continue;
			}
			scarpRate=Settings.mrpSetting.getScarpRateByPN(wkpp.getPn());//��ȡ������
			if(scarpRate==null) {
				logger.error("���ܲ����������д���������ƻ�ʱ����Ʒ����["+wkpp.getPn()+"]û�б����ʡ���ȷ�Ϻ����Ƿ���ȷ��������MRPSettings����ӱ����ʡ�");
				return null;
			}
			ceilValue=Settings.mrpSetting.getCeilingValueByPN(wkpp.getPn());//��ȡ����ȡ��ֵ
			if(ceilValue==null) {
				logger.error("���ܲ����������д���������ƻ�ʱ����Ʒ����["+wkpp.getPn()+"]û������ȡ��������ȷ�Ϻ����Ƿ���ȷ��������MRPSettings���������ȡ������");
				return null;
			}
			demandMatrix.setData(rowindex, colindex, Math.ceil(wkpp.getQty()*(1+scarpRate)/ceilValue)*ceilValue);
		}
		//д��������ݣ��۳�ʵ����������
		for(ProductionContent_Week wkprod:pdwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkprod.getOutput());	//���ݳ�Ʒ�Ż�ȡ������
			if(rowindex==null) {
				logger.error("���ܲ���������������������еĳ�Ʒ����["+wkprod.getOutput()+"]�����ڱ�ͷ�ж�λ��������ϸ��"+wkprod.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkprod.getYear(),wkprod.getWeek()));	//�����������ȷ��������
			if(colindex==null) {
				logger.warn("�����������е���/�������["+String.format("%dwk%02d",wkprod.getYear(),wkprod.getWeek())+"]�������б�ͷ�ж�λ��������ϸ��"+wkprod.toString()+".���ܳ�Խ�˹涨����������������������������");
				continue;
			}
			scarpRate=Settings.mrpSetting.getScarpRateByPN(wkprod.getOutput());//��ȡ������
			if(scarpRate==null) {
				logger.error("���ܲ����������д������������ʱ����Ʒ����["+wkprod.getOutput()+"]û�б����ʡ���ȷ�Ϻ����Ƿ���ȷ��������MRPSettings����ӱ����ʡ�");
				return null;
			}
			ceilValue=Settings.mrpSetting.getCeilingValueByPN(wkprod.getOutput());//��ȡ����ȡ��ֵ
			if(ceilValue==null) {
				logger.error("���ܲ����������д������������ʱ����Ʒ����["+wkprod.getOutput()+"]û������ȡ��������ȷ�Ϻ����Ƿ���ȷ��������MRPSettings���������ȡ������");
				return null;
			}
			Double ori=(Double)demandMatrix.getData(rowindex, colindex);	//�Ȼ�ȡԭ�ȵ�����
			if(ori==null) ori=0.0;									//���û��ԭ�������ʼ��Ϊ0
			demandMatrix.setData(rowindex, colindex, Math.ceil((ori-wkprod.getQty())*(1+scarpRate)/ceilValue)*ceilValue);	//����ֵ����Ϊ��ֵ
		}
		return demandMatrix;
	}
	
}
