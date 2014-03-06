

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandDataWriterDB;
import com.logsys.demand.DemandProcess;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.exportDemandToExcel("e:\\test11.xlsx", null, new Date("2013/9/1"), new Date("2014/1/2"));
		DemandDataWriterDB.backupDemandData(null, new Date("2014/5/1"), new Date("2014/7/1"));
	}
	
}
