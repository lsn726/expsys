import com.logsys.bom.BOMDataWriterDB;


public class Main {

	public static void main(String[] args) {
		//DemandProcess.importDemandFromExcel("e:\\Routine.xlsx",null);
		//DemandProcess.exportDemandToExcel("e:\\ExportedDemand.xlsx", null, new Date(), null);
		//List<BOMContent> bomlist=BOMDataReaderDB.getComplBOMByPN("22271371");
		System.out.println(BOMDataWriterDB.backupComplBOMFromTopNode("12345678", null));
		//BOMNode topnode=BOMUtil.bomListToBomNodeStructure(bomlist);
		//System.out.print(BOMDataReaderDB.getNodesByLevel(0).size());
	}

}