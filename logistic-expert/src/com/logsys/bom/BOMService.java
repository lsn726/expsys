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
	
	public static void test() {}
	
}