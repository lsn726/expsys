

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandDataWriterDB;
import com.logsys.demand.DemandProcess;

public class Main {

	public static void main(String[] args) {
		DemandProcess.exportDemandToExcel("e:\\123321.xlsx", null, new Date(), null);
	}
	
}