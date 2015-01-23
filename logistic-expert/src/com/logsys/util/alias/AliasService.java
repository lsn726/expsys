package com.logsys.util.alias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ����������
 * @author lx8sn6
 */
public class AliasService {

	private static Logger logger=Logger.getLogger(AliasService.class);
	
	/**�����б�*/
	private static List<AliasContent> aliaslist;
	
	/**��׼����->�������Ƶ�ӳ��ͼ*/
	private static Map<String,List<String>> std_to_aliasmap;
	
	/**��������->��׼���Ƶ�ӳ��ͼ*/
	private static Map<String,String> alias_to_stdmap;
	
	//��̬��ʼ����������ģ��
	static {
		if(!init()) {
			logger.fatal("�����������󣬱��������ʼ��ʧ�ܣ�������ֹ��");
			System.exit(-1);
		}
	}
	
	/**
	 * ��̬��ʼ������������
	 * @return �ɹ�true/ʧ��false
	 */
	private static boolean init() {
		if(!initAliasList()) {
			logger.error("�����ݿ��ж�ȡ�����б���ִ�����ֹ��ʼ����������");
			return false;
		}
		if(!initStdToAliasMap()) {
			logger.error("��ʼ����׼���������ĵ�ͼ���ִ�����ֹ��ʼ����������");
			return false;
		}
		if(!initAliasToStdmap()) {
			logger.error("��ʼ����������׼�����ִ�����ֹ��ʼ����������");
			return false;
		}
		return true;
	}
	
	/**
	 * ��ʼ�������б�
	 * @return �ɹ�true/ʧ��false
	 */
	private static boolean initAliasList() {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Exception ex) {
			logger.error("Session��������:"+ex);
			return false;
		}
		String hql="from AliasContent";
		Query query;
		try {
			query=session.createQuery(hql);
			aliaslist=query.list();
		} catch(Exception ex) {
			logger.error("��ʼ�������б���ִ���",ex);
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	/**
	 * ��ʼ����׼��������ͼ
	 * @return �ɹ�true/ʧ��false
	 */
	private static boolean initStdToAliasMap() {
		if(aliaslist==null) {
			logger.error("���ܳ�ʼ����׼��������ͼ����δ�����ݿ�����ȡ����������ݡ�");
			return false;
		}
		std_to_aliasmap=new HashMap<String,List<String>>();
		String stdname;
		//��ʼ��������ʼ����׼����->�����б�
		for(AliasContent acont:aliaslist) {
			stdname=acont.getStdname();
			if(!std_to_aliasmap.containsKey(stdname))		//���û�а�����׼���ƣ��򴴽��µ�Key��List�ԡ�
				std_to_aliasmap.put(stdname, new ArrayList<String>());
			std_to_aliasmap.get(stdname).add(acont.getAliasname());
		}
		return true;
	}
	
	/**
	 * ��ʼ��������Ӧ��׼��ͼ
	 * @return �ɹ�true/ʧ��false
	 */
	private static boolean initAliasToStdmap() {
		if(aliaslist==null) {
			logger.error("���ܳ�ʼ����������׼����ͼ����δ�����ݿ�����ȡ����������ݡ�");
			return false;
		}
		alias_to_stdmap=new HashMap<String,String>();
		String aliasname;
		//��ʼ��������ʼ����������׼��ͼ
		for(AliasContent acont:aliaslist) {
			aliasname=acont.getAliasname();
			if(alias_to_stdmap.containsKey(aliasname)) {
				logger.error("��ʼ����������׼��ͼ���ִ���һ������ͬʱӵ�ж����׼���ơ�");
				return false;
			}
			alias_to_stdmap.put(aliasname, acont.getStdname());
		}
		return true;
	}

	/**
	 * ���³�ʼ�����������ⲿ�ӿڡ�
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean reInitAliasService() {
		return init();
	}
	
	/**
	 * �ӱ�����ȡ��׼��
	 * @param aliasname ����
	 * @return ��׼����/û�б�׼����null
	 */
	public static String getStdNameByAliasName(String aliasname) {
		if(!alias_to_stdmap.containsKey(aliasname)) {
			logger.info("�����ҵ�����["+aliasname+"]����Ӧ�ı�׼���ơ�");
			return null;
		} else
			return alias_to_stdmap.get(aliasname);
	}
	
	/**
	 * �ӱ�׼����ȡ�����б�
	 * @param stdname ��׼����
	 * @return �����б�/û�б����б�null
	 */
	public static List<String> getAliasNameListByStdName(String stdname) {
		if(!std_to_aliasmap.containsKey(stdname)) {
			logger.info("�����ҵ���׼��["+stdname+"]����Ӧ�ı����б�.");
			return null;
		} else
			return std_to_aliasmap.get(stdname);
	}
	
}