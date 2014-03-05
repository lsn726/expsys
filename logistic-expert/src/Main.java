

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandDataWriterExcel;
import com.logsys.demand.DemandUtil;

public class Main {

	public static void main(String[] args) {
		//List<DemandContent> list=DemandDataReaderFile.getDataFromFile("E:\\DataWarehouse\\Demand\\Email_Audi20140225.xlsx");
		//Map map=DemandUtil.demListToMapByPn(list);
		//System.out.print(DemandDataWriterDB.updateOrInsert(map));
		Set<String> pn=new HashSet<String>();
		pn.add("22271371");
		pn.add("22271373");
		pn.add("22271375");
		pn.add("22271376");
		List<DemandContent> demlist=DemandDataReaderDB.getDataFromDB(pn, new Date("2014/6/1"), new Date("2014/7/1"));
		Map<String,Map<Date,DemandContent>> demmap=DemandUtil.demListToMapByPn(demlist);
		DemandDataWriterExcel.writeToExcel("e:\\test.xlsx", demmap);
	}
	
}
