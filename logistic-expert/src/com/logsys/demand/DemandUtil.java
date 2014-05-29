package com.logsys.demand;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/***
 * �������ݹ�����
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	private static final Date DATE_MAX=new Date("2199/12/31");
	
	private static final Date DATE_MIN=new Date("1910/12/31");
	
	/**�ַ���ǰ׺����Сֵ*/
	public static final String PREFIX_MINDATE="MinDate-";
	
	/**�ַ���ǰ׺�����ֵ*/
	public static final String PREFIX_MAXDATE="MaxDate-";
	
	/**�ܼ�¼�ַ���*/
	public static final String TOTAL_STR="TotalRecord";
	
	/**
	 * �ھ��� ����demListToMapByPn�����ĵ���demandmap(�ͺ�)value�в�����С�������ڵĺ���.
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
	 * �����˳���List->Map<�ͺ�,Map<����,DemandBean>>,��ͬ�ͺ���ͬ���ڵ����󽫱��ϲ�
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
		Date demdate;
		for(DemandContent demand:demandlist) {
			if(demand==null) continue;
			pn=demand.getPn();
			if(!demandmap.containsKey(pn))						//����ͺŲ����ڣ����½�Map��д��
				demandmap.put(pn, new HashMap<Date,DemandContent>());
			demdate=demand.getDate();
			if(demandmap.get(pn).containsKey(demdate))			//����Ѿ��и��ͺ��¸����ڵ�dem����ϲ�����
				demandmap.get(pn).get(demdate).setQty(demandmap.get(pn).get(demdate).getQty()+demand.getQty());
			else
				demandmap.get(pn).put((Date)demand.getDate().clone(), demand);	//д����������
		}
		return demandmap;
	}
	
	/**
	 * ��ȡList<DemandContent>��ʱ�����Сֵ�����ֵ���������
	 * @param demlist ���������б����
	 * @return ʱ����Сֵ�����ֵ��ȥ������/null
	 */
	public static DateInterval getMinMaxDateInDemandList(List<DemandContent> demlist) {
		if(demlist==null) {
			logger.error("���������������б��л�ȡʱ����Сֵ���б�Ϊ�ա�");
			return null;
		}
		if(demlist.size()==0) return null;
		Date mindate=demlist.get(0).getDate();
		Date maxdate=demlist.get(0).getDate();
		for(DemandContent demcont:demlist) {
			if(mindate.after(demcont.getDate()))
				mindate=demcont.getDate();
			if(maxdate.before(demcont.getDate()))
				maxdate=demcont.getDate();
		}
		return new DateInterval(mindate,maxdate);
	}
	
	/**
	 * ��BackupDemandList�л�ȡ�����С�İ汾����Map����ʽΪ��PREFIX_MINDATE+"�ͺ�"->���ͺ���С���� ��PREFIX_MAXDATE+"�ͺ�"->���ͺ�������ڡ�PREFIX_MINDATE+TOTAL_STR->������С���ڡ�PREFIX_MAXDATE+TOTAL_STR->�����������
	 * @param bkupdemlist ���������б��ܶ��� 
	 * @return ����汾���ڵ������Сֵ������ӳ�����/null
	 */
	public static Map<String,Date> getMinMaxVersionDateInBackupDemandList(List<DemandBackupContent_Week> bkupdemwklist) {
		if(bkupdemwklist==null) {
			logger.error("������ȡ���ݱ��������б�������С�汾���ݣ������б����ֵΪnull.");
			return null;
		}
		Map<String,Date> verMap=new HashMap<String,Date>();
		Date tempdate;		//��ʱ���ڱ���
		Date contdate;		//��������ʱ��
		String pn;			//���Ϻ�
		for(DemandBackupContent_Week bkdemcont:bkupdemwklist) {	//����ѭ�������Сֵ
			pn=bkdemcont.getPn();
			contdate=bkdemcont.getVersion();				//�԰汾Ϊ�б�����
			tempdate=verMap.get(PREFIX_MINDATE+pn);			//��ɸѡ��Сֵ
			if(tempdate==null||tempdate.after(contdate))	//��СֵΪ�գ�������Сֵ����ֵ֮����д���µ���Сֵ
				verMap.put(PREFIX_MINDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+pn);			//��ɸѡ���ֵ
			if(tempdate==null||tempdate.before(contdate))	//���ֵΪ�գ��������ֵ����ְ֮ǰ����д���µ����ֵ
				verMap.put(PREFIX_MAXDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MINDATE+TOTAL_STR);	//��ɸѡ��ֵ��Сֵ
			if(tempdate==null||tempdate.after(contdate))
				verMap.put(PREFIX_MINDATE+TOTAL_STR, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+TOTAL_STR);	//��ɸ��ֵѡ���ֵ
			if(tempdate==null||tempdate.before(contdate))
				verMap.put(PREFIX_MAXDATE+TOTAL_STR, contdate);
		}
		return verMap;
	}
	
	/**
	 * ��BackupDemandList�л�ȡ�����С����������Map����ʽΪ��"Min-�ͺ�"->���ͺ���С���� ��"Max-�ͺ�"->���ͺ�������ڡ�"Min-TotalRecord"->������С���ڡ�"Max-TotalRecord"->�����������
	 * @param bkupdemlist ���������б��ܶ��� 
	 * @return �����������ڵ������Сֵ������ӳ�����/null
	 */
	public static Map<String,Date> getMinMaxDemandDateInBackupDemandList(List<DemandBackupContent_Week> bkupdemwklist) {
		if(bkupdemwklist==null) {
			logger.error("������ȡ���ݱ��������б�������С�������ݣ������б����ֵΪnull.");
			return null;
		}
		Map<String,Date> verMap=new HashMap<String,Date>();
		Date tempdate;		//��ʱ���ڱ���
		Date contdate;		//��������ʱ��
		String pn;			//���Ϻ�
		for(DemandBackupContent_Week bkdemcont:bkupdemwklist) {	//����ѭ�������Сֵ
			pn=bkdemcont.getPn();
			contdate=bkdemcont.getDate();					//������Ϊ�б�����
			tempdate=verMap.get(PREFIX_MINDATE+pn);			//��ɸѡ��Сֵ
			if(tempdate==null||tempdate.after(contdate))	//��СֵΪ�գ�������Сֵ����ֵ֮����д���µ���Сֵ
				verMap.put(PREFIX_MINDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+pn);			//��ɸѡ���ֵ
			if(tempdate==null||tempdate.before(contdate))	//���ֵΪ�գ��������ֵ����ְ֮ǰ����д���µ����ֵ
				verMap.put(PREFIX_MAXDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MINDATE+TOTAL_STR);	//��ɸѡ��ֵ��Сֵ
			if(tempdate==null||tempdate.after(contdate))
				verMap.put(PREFIX_MINDATE+TOTAL_STR, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+TOTAL_STR);	//��ɸ��ֵѡ���ֵ
			if(tempdate==null||tempdate.before(contdate))
				verMap.put(PREFIX_MAXDATE+TOTAL_STR, contdate);
		}
		return verMap;
	}
	
}