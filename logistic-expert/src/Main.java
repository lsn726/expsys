

import java.util.List;
import java.util.Map;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandDataReaderFile;
import com.logsys.demand.DemandUtil;

public class Main {

	public static void main(String[] args) {
		List<DemandContent> list=DemandDataReaderFile.getDataFromFile("E:\\DataWarehouse\\Demand\\Email_Audi20140225.xlsx");
		System.out.println("Max:"+DemandUtil.getMaxDate(list, "22271375")+"/"+"Min:"+DemandUtil.getMinDate(list, "22271375"));
		Map map=DemandUtil.demListToMapByPn(list);
		System.out.println(map);
	}
	
}
