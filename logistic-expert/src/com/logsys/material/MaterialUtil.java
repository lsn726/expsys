package com.logsys.material;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * ���Ϲ�����
 * @author lx8sn6
 *
 */
public class MaterialUtil {

	private static Logger logger=Logger.getLogger(MaterialUtil.class);
	
	/**
	 * ��matlist�����б�ת��ΪMap<pn,MaterialContent>����ͼ
	 * @param matlist �����б�
	 * @return ����ͼ
	 */
	public static Map<String,MaterialContent> matListToMap(List<MaterialContent> matlist) {
		if(matlist==null) {
			logger.error("����ת��Ϊ����ͼ�������б�Ϊ��.");
			return null;
		}
		Map<String,MaterialContent> matmap=new HashMap<String,MaterialContent>();
		if(matlist.size()==0) return matmap;
		for(MaterialContent matcont:matlist)
			matmap.put(matcont.getPn(), matcont);
		return matmap;
	}
	
	/**
	 * ��ȡ�������������ͼ
	 * @param pnset ���ϼ�
	 * @return Map<pn,˳��>����
	 */
	public static Map<String,Integer> getOrderedMatMap(Set<String> pnset) {
		List<MaterialContent> orderedlist=MaterialDataReaderDB.getDataFromDB(pnset, "buy", true, true);
		if(orderedlist==null) return null;
		Map<String,Integer> orderedmap=new HashMap<String,Integer>();
		int counter=1;
		for(MaterialContent mcont:orderedlist)		//��orderedslistת��Ϊorderedmap
			orderedmap.put(mcont.getPn(), counter++);
		return orderedmap;
	}
	
	/**
	 * �������б�����ȡ����pn��
	 * @param matlist �����б�
	 * @return ����pn��
	 */
	public static Set<String> getPnSet(List<MaterialContent> matlist) {
		if(matlist==null) {
			logger.error("�޷���ȡ���Ϻ��б�.");
			return null;
		}
		Set<String> pnset=new HashSet<String>();
		for(MaterialContent mcont:matlist)
			pnset.add(mcont.getPn());
		return pnset;
	}
	
	
	
}