package com.logsys.bom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.util.DateTimeUtils;

/**
 * BOM服务对象
 * @author lx8sn6
 */
public class BOMService {

	private static Logger logger=Logger.getLogger(BOMService.class);
	
	/**BOM版本->[Map:Asmpn->BOMContent列表],包含所有的BOM数据*/
	private static Map<Calendar,Map<String,List<BOMContent>>> bommap;
	
	/**静态初始化块*/
	static {
		initBomSerivce();
	}
	
	private BOMService() {};

	/**
	 * 初始化Bom服务类
	 */
	private static void initBomSerivce() {
		List<BOMContent> oribomlist=new ArrayList<BOMContent>();
		oribomlist=BOMDBReader.getBOMList(null);		//从数据库读取BOM的Node列表
		if(oribomlist==null) {
			logger.fatal("初始化BOM服务模块出现错误。");
			return;
		}
		bommap=new HashMap<Calendar,Map<String,List<BOMContent>>>();
		Calendar cal;
		String asmpn;
		Map<String,List<BOMContent>> submap;
		for(BOMContent bcont: oribomlist) {				//遍历整理BOM服务列表,将之拆分为bommap对象
			cal=bcont.getVersion();
			if(!bommap.containsKey(cal))				//如果没有对应版本的对象，则需先增加对应的HashMap容器
				bommap.put((Calendar)cal.clone(), new HashMap<String,List<BOMContent>>());
			submap=bommap.get(cal);
			asmpn=bcont.getAsmpn();
			if(!submap.containsKey(asmpn))				//如果没有对应的组成件列表，则需要先增加列表对象
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