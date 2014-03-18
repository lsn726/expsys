import java.util.List;

import com.logsys.material.MaterialContent;
import com.logsys.material.MaterialDataReaderDB;

public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\",null);
		//DemandProcess.exportDemandToExcel("e:\\123321.xlsx", null, new Date(), null);
		List<MaterialContent> matlist=MaterialDataReaderDB.getDataFromDB(null,"buy", true);
		System.out.print(matlist.size());
	}

}