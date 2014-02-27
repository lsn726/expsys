package com.logsys.demand;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/***
 * �������ݹ�����
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	/**
	 * ��demandlist�в���ָ��part bumber��������������
	 * @param demandlist ���������б�
	 * @param pn ָ����part number
	 * @return ���ص�ָ��pn��С��������
	 */
	public static Date getMinDate(List<DemandContent> demandlist,String pn) {
		if(demandlist==null) {
			logger.error("���������б�Ϊ�ա�");
			return null;
		}
		if(demandlist.size()==0) {
			logger.warn("���������б������κ����ݡ�");
			return null;
		}
		Date mindate=new Date("2199/12/31");		//����һ���������
		for(DemandContent dcontent:demandlist)
			if(dcontent.getPn().equals(pn))			//����ͺ����
				if(mindate.after(dcontent.getDate()))	//�����С�����ڴ���������֮��
					mindate=(Date) dcontent.getDate().clone();			//����Ϊ��С����
		return mindate;
	}
	
	/**
	 * ��demandlist�в���ָ��part bumber��������������
	 * @param demandlist ���������б�
	 * @param pn ָ���ͺ�
	 * @return ������������
	 */
	public static Date getMaxDate(List<DemandContent> demandlist,String pn) {
		if(demandlist==null) {
			logger.error("���������б�Ϊ�ա�");
			return null;
		}
		if(demandlist.size()==0) {
			logger.warn("���������б������κ����ݡ�");
			return null;
		}
		Date maxdate=new Date("1910/12/31");		//����һ����С����
		for(DemandContent dcontent:demandlist)
			if(dcontent.getPn().equals(pn))			//����ͺ����
				if(maxdate.before(dcontent.getDate()))			//�����С�����ڴ���������֮��
					maxdate=(Date) dcontent.getDate().clone();	//����Ϊ��С����
		return maxdate;
	}
	
	/**
	 * �����˳���List->Map<�ͺ�,Map<����,DemandBean>>
	 * @param demandlist �����б�
	 * @return ��ʽ�����Map����
	 */
	public static Map<String,Map<Date,DemandContent>> demListToMapByPn(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("�������ݲ���Ϊ�ա�");
			return null;
		}
		String pn;
		Map<String,Map<Date,DemandContent>> demandmap=new HashMap<String,Map<Date,DemandContent>>();
		for(DemandContent demand:demandlist) {
			if(demandlist==null) continue;
			pn=demand.getPn();
			if(!demandmap.containsKey(pn))						//����ͺŲ����ڣ����½�Map��д��
				demandmap.put(pn, new HashMap<Date,DemandContent>());
			demandmap.get(pn).put((Date)demand.getDate().clone(), demand);	//д����������
		}
		return demandmap;
	}
	
	/**
	 * ΪdemListToMapByPn()���ɵ�������е�һ�����ͺ��������Ϊ0�Ŀ������,�����ĳ��û���������ݣ��������������������ݣ�������Ϊ0
	 * @param demandmap ��demListToMapByPn()�������ɵ�����map�еĵ����ͺ�����
	 * @param begin ���ɿ���������ʼʱ��
	 * @param end ���ɿ������Ľ���ʱ��
	 * @return �������������
	 */
	public static int fillEmptyDemandNodes(Map<Date,DemandContent> demandmap, Date begin, Date end) {
		if(demandmap==null||begin==null||end==null) {
			logger.error("����Ϊ��");
			return -1;
		}
		if(begin.after(end)) {
			logger.error("��ʼʱ�����ڽ���ʱ�䡣");
			return -1;
		}
		int counter=0;
		Date index=(Date)begin.clone();
		while(true) {
			if(index.after(end)) break;
			if(!demandmap.containsKey(index)) {		//���û��������ڣ������սڵ�
				
			}
		}
	}

}