

import java.util.List;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandDataReaderFile;
import com.logsys.demand.DemandDataWriterDB;

public class Main {

	public static void main(String[] args) {
		List<DemandContent> list=DemandDataReaderFile.getDataFromFile("E:\\DataWarehouse\\Demand\\Email_Audi20140225.xlsx");
		int counter=DemandDataWriterDB.writeToDatabase(list);
		System.out.println("Counter:"+counter);
	}
	
}
