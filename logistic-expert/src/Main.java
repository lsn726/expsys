import java.util.List;

import com.logsys.bom.BOMContent;
import com.logsys.bom.BOMDataReaderExcel;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Demand_CQAC.xlsx",null);
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);
		List<BOMContent> bomlist=BOMDataReaderExcel.getDataFromExcel_CS12("E:\\DataWarehouse\\BOM\\CS12_22265581.xlsx");
		System.out.print(bomlist.size());
	}

}