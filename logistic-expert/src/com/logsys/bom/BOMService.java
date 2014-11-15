package com.logsys.bom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.util.DateTimeUtils;

/**
 * BOM�������
 * @author lx8sn6
 */
public class BOMService {

	private static Logger logger=Logger.getLogger(BOMService.class);
	
	/**����BOM*/
	public static int BOM_LEVEL_SINGLE=100;
	
	/**���BOM*/
	public static int BOM_LEVEL_MULTI=101;
	
	/**BOM�汾->[Map:Asmpn->BOMContent�б�],�������е�BOM����*/
	private static Map<Calendar,Map<String,List<BOMContent>>> bommap;
	
	/**��̬��ʼ����*/
	static {
		initBomSerivce();
	}
	
	private BOMService() {};

	/**
	 * ��ʼ��Bom������
	 */
	private static void initBomSerivce() {
		List<BOMContent> oribomlist=new ArrayList<BOMContent>();
		oribomlist=BOMDBReader.getBOMList(null);		//�����ݿ��ȡBOM��Node�б�
		if(oribomlist==null) {
			logger.fatal("��ʼ��BOM����ģ����ִ���");
			return;
		}
		bommap=new HashMap<Calendar,Map<String,List<BOMContent>>>();
		Calendar cal;
		String asmpn;
		Map<String,List<BOMContent>> submap;
		for(BOMContent bcont: oribomlist) {				//��������BOM�����б�,��֮���Ϊbommap����
			cal=bcont.getVersion();
			if(!bommap.containsKey(cal))				//���û�ж�Ӧ�汾�Ķ������������Ӷ�Ӧ��HashMap����
				bommap.put((Calendar)cal.clone(), new HashMap<String,List<BOMContent>>());
			submap=bommap.get(cal);
			asmpn=bcont.getAsmpn();
			if(!submap.containsKey(asmpn))				//���û�ж�Ӧ����ɼ��б�����Ҫ�������б����
				submap.put(new String(asmpn), new ArrayList<BOMContent>());
			submap.get(asmpn).add(bcont);
		}
		/** Debug Printer
		for(Calendar cald:bommap.keySet()) {
			submap=bommap.get(cald);
			for(String str:submap.keySet())
				System.out.println("["+cald.getTime()+"]->"+"["+str+"]::"+submap.get(str).size());
		}*/
	}
	
	/**
	 * ͨ����ɼ���PN��ȡBOMNode�������а��� ��������BOM�ṹ
	 * @param version BOM�汾��null��Ĭ��Ϊ���°汾
	 * @param asmpn ��ɼ���PartNumber
	 * @param bomtype BOM_LEVEL_SINGLE ����BOM(ֻ����ASMPN�����һ���BOM)/BOM_LEVEL_MULTI ��㼶BOM
	 * @return ���ܳɼ���BOM�ṹ��BOMNode��������ǵײ�ṹ/���ϺŲ����ڣ��򷵻�null.
	 */
	public static BOMNode getBomByAsmPn(Calendar version, String asmpn, int bomtype) {
		if(version==null) {			//����汾Ϊ�գ�����ѡ�������ڡ�
			version=DateTimeUtils.getMinCalendar();
			for(Calendar cal:bommap.keySet())
				if(cal.after(version)) version=cal;
		}
		return getBomByAsmPnRecursion(version,asmpn,bomtype,0);		//��Ϊ��0����ʼ����
	}
	
	/**
	 * getBomByAsmPn�ĵݹ���ú���
	 * @param version �汾,����Ϊnull
	 * @param asmpn ��ɼ���PartNumber
	 * @param bomtype BOM_LEVEL_SINGLE ����BOM(ֻ����ASMPN�����һ���BOM)/BOM_LEVEL_MULTI ��㼶BOM
	 * @param bomlevel �ò�BOM�ĵȼ�������д��BOM����
	 * @return ���ܳɼ���BOM�ṹ��BOMNode��������ǵײ�ṹ/���ϺŲ����ڣ��򷵻�null.
	 */
	private static BOMNode getBomByAsmPnRecursion(Calendar version, String asmpn, int bomtype, int bomlevel) {
		if(version==null) {
			logger.error("�ݹ���ȡBOM�ṹ���ִ���BOM�汾Ϊ�ա�");
			return null;
		}
		Map<String,List<BOMContent>> asmbommap=bommap.get(version);
		if(!asmbommap.containsKey(asmpn))		//�����������BOM���ӽṹ����ȷ��Ϊ�ײ��BOM�ṹ
			return null;
		List<BOMContent> subnodelist=asmbommap.get(asmpn);
		BOMNode firstnode;						//��һ�ڵ�
		BOMNode indexnode;						//�����ڵ�
		String subpn;
		for(BOMContent bcont:subnodelist) {		//�����Ӽ��б���������BOMNode
			subpn=bcont.getSubpn();
			if(bomtype==BOM_LEVEL_SINGLE)		//���Ϊ����BOM����ֱ�ӷ���BOM����
				indexnode=new BOMNode(bcont,bomlevel);
			else if(bomtype==BOM_LEVEL_MULTI) {	//���Ϊ�༶BOM������Ҫ�ݹ���ã�ȷ���Ӳ㼶BOM
				indexnode=getBomByAsmPnRecursion(version,subpn,bomtype,bomlevel);
				
			}
		}
	}

}