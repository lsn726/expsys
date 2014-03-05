package com.logsys.demand;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/***
 * �������ݹ�����
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	private static final Date DATE_MAX=new Date("2199/12/31");
	
	private static final Date DATE_MIN=new Date("1910/12/31");
	
	/**
	 * �ھ��� ����demListToMapByPn�����ĵ���demandmap(�ͺ�)�в�����С���ڵĺ���.
	 * @param demandlist ���������б�
	 * @return ���ص��ҵ�����С��������
	 */
	public static Date getMinDate(Map<Date,DemandContent> demandmap) {
		if(demandmap==null) {
			logger.error("���������б�Ϊ�ա�");
			return null;
		}
		if(demandmap.size()==0) {
			logger.warn("���������б������κ����ݡ�");
			return null;
		}
		Date mindate=(Date)DATE_MAX.clone();//����һ���������
		for(Date date:demandmap.keySet())
			if(mindate.after(date))			//�����С�����ڴ���������֮��
				mindate=date;				//����Ϊ��С����
		return (Date)mindate.clone();
	}
	
	/**
	 * �ھ��� ����demListToMapByPn�����ĵ���demandmap(�ͺ�)�в���������ڵĺ���.
	 * @param demandlist ���������б�
	 * @return ���ص��ҵ���������������
	 */
	public static Date getMaxDate(Map<Date,DemandContent> demandmap) {
		if(demandmap==null) {
			logger.error("���������б�Ϊ�ա�");
			return null;
		}
		if(demandmap.size()==0) {
			logger.warn("���������б������κ����ݡ�");
			return null;
		}
		Date maxdate=(Date)DATE_MIN.clone();//����һ����С����
		for(Date date:demandmap.keySet())
			if(maxdate.before(date))		//�����С�����ڴ���������֮��
				maxdate=date;				//����Ϊ��С����
		return (Date)maxdate.clone();
	}
	
	/**
	 * ����DemandUtil.demListToMapByPn()��������Ľ�����������ͺŵ���Сʱ�䡣
	 * @param demmap
	 * @return ���ص��ҵ���������������
	 */
	public static Date getMinDateInMultiModel(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("�������ݲ���Ϊ��");
			return null;
		}
		Date mindate=(Date)DATE_MAX.clone();
		Date temp;
		for(String model:demmap.keySet()) {
			temp=getMinDate(demmap.get(model));
			if(mindate.after(temp))
				mindate=temp;
		}
		return mindate;
	}
	
	/**
	 * ����DemandUtil.demListToMapByPn()��������Ľ�����������ͺŵ���Сʱ�䡣
	 * @param demmap
	 * @return ���ص��ҵ���������������
	 */
	public static Date getMaxDateInMultiModel(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("�������ݲ���Ϊ��");
			return null;
		}
		Date maxdate=(Date)DATE_MIN.clone();
		Date temp;
		for(String model:demmap.keySet()) {
			temp=getMaxDate(demmap.get(model));
			if(maxdate.before(temp))
				maxdate=temp;
		}
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
		if(demandmap.size()==0) return 0;
		if(begin.after(end)) {
			logger.error("��ʼʱ�����ڽ���ʱ�䡣");
			return -1;
		}
		int counter=0;
		Date index=(Date)begin.clone();
		Calendar cal=Calendar.getInstance();
		DemandContent dcontent;
		String pn=demandmap.get(demandmap.keySet().toArray()[0]).getPn();
		while(true) {
			if(index.after(end)) break;
			if(!demandmap.containsKey(index)) {		//���û��������ڣ������սڵ�
				dcontent=new DemandContent();
				dcontent.setDate((Date)index.clone());
				dcontent.setPn(pn);
				dcontent.setQty(0);
				dcontent.setDlvfix(0);
				demandmap.put(dcontent.getDate(), dcontent);
				counter++;
			}
			cal.setTime(index);
			cal.add(Calendar.DAY_OF_MONTH,1);
			index=cal.getTime();
		}
		return counter;
	}
	
	

}