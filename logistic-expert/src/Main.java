import java.util.Date;
import java.util.List;

import com.logsys.bom.BOMContent;
import com.logsys.bom.BOMDataReaderDB;
import com.logsys.bom.BOMDataReaderExcel;
import com.logsys.bom.BOMDataWriterDB;
import com.logsys.bom.BOMUtil;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Excel.xlsx",null);
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);
		//BOMNode topnode=BOMUtil.bomListToBomNodeStructure(bomlist);
		System.out.print(BOMDataReaderDB.getNodesByLevel(0).size());
	}

}