package com.logsys.setting.pd.bwi;

import java.util.HashMap;
import java.util.Map;

import com.logsys.production.ProductionInterval;
import com.logsys.util.Location;

public abstract class BWIPdExcelInfo {

	/**����HashMap������NULL���ߡ���ΪValue�����ֻ�ܲ������ַ�����ǿ��ַ�����*/
	public static final String EMPTY_STR="EMPTR_STR_INDENTIFIER";
	
	/**����Sheet��*/
	private static final String CONFIGURE_SHEETNAME="Configuration";
	
	/**�����������ڲ��������еĲ�*/
	public static final int RELCOL_PARTNUM_OUTPUTQTY=-2;
	
	/**��������������ڲ��������еĲ�*/
	public static final int RELCOL_OPERQTY_OUTPUTQTY=-3;
	
	/**����Sheet�У�����������������*/
	public static final Location PLLOC=new Location(1,0);
	
	/**��֤ӳ��ͼ��λ��->��֤�ַ���*/
	protected static final Map<Location,String> VERIFYMAP_LOC_STR=new HashMap<Location,String>();
	
	/**��Ʒӳ��ͼ������->��Ʒ��׼Part Number*/
	protected static final Map<String,String> PRODMAP_ALIAS_STDPN=new HashMap<String,String>();
	
	/**��������ӳ��ͼ��λ��->����������󣬴�ӳ��ͼ���������п��ܼ�¼�������ݵ�λ��*/
	protected static final Map<Location,ProductionInterval> PRODMAP_LOC_INTERVAL=new HashMap<Location,ProductionInterval>();
	
	public BWIPdExcelInfo() {
		initLocVerifyStrMap();		//��ʼ��ӳ���:λ��->��֤�ַ���
		initProdAliasStdPnMap();	//��ʼ��ӳ���:����->��׼��
		initProdInterval();			//��ʼ��ӳ���:��������ö��->�����������
	}
	
	/**
	 * ��ʼ��ӳ��ͼ��λ��->��֤�ַ���
	 */
	protected abstract void initLocVerifyStrMap();
	
	/**
	 * ��ʼ��ӳ��ͼ������->��Ʒ��׼Part Number
	 */
	protected abstract void initProdAliasStdPnMap();
	
	/**
	 * ��ʼ��ӳ��ͼ����������ö��->�����������
	 */
	protected abstract void initProdInterval();
	
	/**
	 * ��ȡ����Sheet����
	 * @return ����Sheet����
	 */
	public static String getConfigSheetName() {
		return CONFIGURE_SHEETNAME;
	}
	
	/**
	 * ��ȡ��������λ��
	 * @return ��������λ��
	 */
	public abstract Location getDateLocation();
	
	/**
	 * ��ȡ��֤�ַ���ӳ��ͼ
	 * @return ��֤ӳ��ͼ
	 */
	public Map<Location,String> getLocVerifyStrMap() {
		return VERIFYMAP_LOC_STR;
	}
	
	/**
	 * ��ȡ��Ʒ����->��׼��ӳ��ͼ
	 * @return ��֤ӳ��ͼ
	 */
	public Map<String,String> getProdAliasStdPnMap() {
		return PRODMAP_ALIAS_STDPN;
	}
	
	/**
	 * ��ȡλ��->��������ӳ���
	 * @return λ��->��������ӳ���
	 */
	public Map<Location,ProductionInterval> getLocIntervalMap() {
		return PRODMAP_LOC_INTERVAL;
	}
	
}
