package com.logsys.setting.pd.bwi.pr;

import com.logsys.production.ProductionInterval;
import com.logsys.production.ProductionInterval.PdInterval;
import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--PR--电镀线
 * @author lx8sn6
 */
public class BWIPdExcelInfoPR_CrPlating extends BWIPdExcelInfoPR {

	@Override
	protected void initProdInterval() {
		PRODMAP_LOC_INTERVAL.put(new Location(11,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(12,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(13,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(14,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(15,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(16,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(17,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(18,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(19,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(20,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(21,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(22,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(23,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(28,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(29,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(30,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(31,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(32,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(33,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(34,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(35,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(40,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(41,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(42,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(43,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(44,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(45,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(46,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(11,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(12,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(13,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(14,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(15,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(16,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(17,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(18,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(19,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(20,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(21,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(22,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(23,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(28,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(29,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(30,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(31,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(32,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(33,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(34,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(35,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(40,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(41,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(42,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(43,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(44,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(45,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(46,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
	}
	
}
