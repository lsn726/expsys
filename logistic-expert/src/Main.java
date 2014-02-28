

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandDataReaderFile;
import com.logsys.demand.DemandDataWriterDB;
import com.logsys.demand.DemandUtil;

public class Main {

	public static void main(String[] args) {
		List<DemandContent> list=DemandDataReaderFile.getDataFromFile("E:\\DataWarehouse\\Demand\\Email_Audi20140225.xlsx");
		Map map=DemandUtil.demListToMapByPn(list);
		System.out.print(DemandDataWriterDB.updateOrInsert(map));
	}
	
}
